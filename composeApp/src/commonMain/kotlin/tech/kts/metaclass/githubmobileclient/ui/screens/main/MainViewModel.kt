package tech.kts.metaclass.githubmobileclient.ui.screens.main

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
class MainViewModel(
    private val search: SearchRepositoriesUseCase,
    private val mapper: UiRepositoryMapper
) : ViewModel() {
    private var currentSearchJob: Job? = null
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()
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
        val nextPageNum = _state.value.nextPageNum ?: return
        if (_state.value.isLoadingNextPage || _state.value.isCachedDataShown) return
        loadPage(_state.value.searchQuery, nextPageNum)
    }

    private fun searchRepositories(query: String) {
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = false,
                    repositories = emptyList(),
                    nextPageNum = null
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
                isLoading = false,
                isLoadingNextPage = false,
                isCachedDataShown = false,
                error = false,
                repositories = emptyList(),
                nextPageNum = null
            )
        }
    }

    private fun loadPage(query: String, pageNum: Int) {
        if (query.isBlank()) return
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingNextPage = true) }
            handleResult(search(query, pageNum), accumulated = _state.value.repositories)
        }
    }

    private fun handleResult(
        result: SearchResult<GitHubRepository>,
        accumulated: List<RepositoryUiState>
    ) {
        when (result) {
            is SearchResult.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingNextPage = false,
                        isCachedDataShown = false,
                        error = false,
                        repositories = accumulated + result.data.map(mapper::toUiState),
                        nextPageNum = result.nextPageNum
                    )
                }
            }

            is SearchResult.Cached -> {
                Napier.e("Search error", result.cause, tag = "Network")
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingNextPage = false,
                        isCachedDataShown = true,
                        repositories = result.data.map(mapper::toUiState),
                        nextPageNum = null
                    )
                }
            }

            is SearchResult.Failure -> {
                Napier.e("Search error", result.cause, tag = "Network")
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingNextPage = false,
                        error = accumulated.isEmpty(),
                        nextPageNum = null
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
