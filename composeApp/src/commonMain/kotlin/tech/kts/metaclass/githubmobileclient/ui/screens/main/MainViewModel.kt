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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.useCases.repositories.SearchRepositoriesResult
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

    private fun searchRepositories(query: String) {
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = false) }

            when (val result = search(query)) {
                is SearchRepositoriesResult.Success -> {
                    _state.update { it.copy(
                        isLoading = false,
                        isCachedDataShown = false,
                        repositories = result.repositories.map(mapper::toUiState))
                    }
                }
                is SearchRepositoriesResult.Cached -> {
                    Napier.e("Search error", result.cause, tag = "Network")
                    _state.update { it.copy(
                        isLoading = false,
                        isCachedDataShown = true,
                        repositories = result.repositories.map(mapper::toUiState))
                    }
                }
                is SearchRepositoriesResult.Failure -> {
                    Napier.e("Search error", result.cause, tag = "Network")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            repositories = emptyList(),
                            error = true
                        )
                    }
                }
            }
        }
    }

    private fun observeSearchQuery() {
        searchQueryFlow
            .debounce(300L)
            .distinctUntilChanged()
            .filter { query -> query.isNotBlank() }
            .onEach { query -> searchRepositories(query) }
            .launchIn(viewModelScope)
    }
}
