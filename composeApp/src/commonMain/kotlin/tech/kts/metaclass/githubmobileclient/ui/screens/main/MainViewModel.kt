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
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoryRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoryRepositoryImpl
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository

@OptIn(FlowPreview::class)
class MainViewModel(
    private val repository: GitHubRepositoryRepository = GitHubRepositoryRepositoryImpl() // TODO: вынести в di контейнер
) : ViewModel() {
    private val searchQueryFlow = MutableStateFlow("")
    private var currentSearchJob: Job? = null
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        searchQueryFlow
            .debounce(300L)
            .distinctUntilChanged()
            .filter { query -> query.isNotBlank() }
            .onEach { query -> searchRepositories(query) }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchQueryFlow.value = query
    }

    fun clearSearch() {
        onSearchQueryChange("")
    }

    private fun searchRepositories(query: String) {
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.searchRepositories(query).fold(
                onSuccess = ::onSearchSuccess,
                onFailure = ::onSearchFailer
            )

        }
    }

    fun onSearchSuccess(repositories: List<GitHubRepository>) {
        _state.update { it.copy(isLoading = false, repositories = repositories) }
    }

    fun onSearchFailer(throwable: Throwable) {
        Napier.e("Search error", throwable, tag = "Network")
        _state.update {
            it.copy(
                isLoading = false,
                repositories = emptyList(),
                error = throwable.message ?: "Unknown error"
            )
        }
    }
}
