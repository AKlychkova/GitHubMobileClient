package tech.kts.metaclass.githubmobileclient.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchResult
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesUseCase

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val search: SearchRepositoriesUseCase,
    private val mapper: UiRepositoryMapper
) : ViewModel() {
    private var currentSearchJob: Job? = null
    private var nextPageNum: Int? = null
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()
    private val searchQueryFlow = MutableStateFlow(_state.value.searchQuery)

    init {
        observeSearchQuery()
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchQueryFlow.value = query
    }

    fun onSearchRetry() {
        searchRepositories(_state.value.searchQuery)
    }

    fun clearSearch() {
        onSearchQueryChange("")
    }

    fun loadNextPage() {
        val currentState = _state.value
        val listState = currentState.listState as? ListUiState.DataShown ?: return
        if (listState.isLoadingNextPage || listState.isCachedDataShown) return

        val nextPageNum = nextPageNum ?: return
        val query = currentState.searchQuery
        val accumulated = listState.repositories

        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            _state.update { current ->
                val currentListState = current.listState as? ListUiState.DataShown
                    ?: return@update current

                current.copy(
                    listState = currentListState.copy(isLoadingNextPage = true)
                )
            }
            val result = search(query, nextPageNum)
            handleResult(result, accumulated)
        }
    }

    private fun searchRepositories(query: String) {
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    listState = ListUiState.Loading
                )
            }
            handleResult(
                result = search(query, 1),
                accumulated = emptyList()
            )
        }
    }

    private fun onBlankSearch() {
        currentSearchJob?.cancel()
        _state.update {
            it.copy(
                listState = ListUiState.DataShown()
            )
        }
        nextPageNum = null
    }

    private fun handleResult(
        result: SearchResult<GitHubRepository>,
        accumulated: List<RepositoryUiState>
    ) {
        when (result) {
            is SearchResult.Success -> {
                _state.update {
                    it.copy(
                        listState = ListUiState.DataShown(
                            repositories = accumulated + result.data.map(mapper::toUiState)
                        )
                    )
                }
                nextPageNum = result.nextPageNum
            }

            is SearchResult.Cached -> {
                Napier.e("Search error", result.cause, tag = "Network")
                _state.update {
                    it.copy(
                        listState = ListUiState.DataShown(
                            repositories = result.data.map(mapper::toUiState),
                            isCachedDataShown = true,
                        )
                    )
                }
                nextPageNum = null
            }

            is SearchResult.Failure -> {
                Napier.e("Search error", result.cause, tag = "Network")
                _state.update {
                    it.copy(
                        listState = ListUiState.Error
                    )
                }
            }
        }
    }


    private fun observeSearchQuery() {
        searchQueryFlow
            .debounce(600L)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isBlank()) {
                    onBlankSearch()
                } else {
                    searchRepositories(query)
                }
            }
            .launchIn(viewModelScope)
    }
}
