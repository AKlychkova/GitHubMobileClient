package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
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
import kotlinx.coroutines.withContext
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoriesRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoriesRepositoryImpl
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class)
class MainViewModel(
    private val repository: GitHubRepositoriesRepository = GitHubRepositoriesRepositoryImpl() // TODO: вынести в di контейнер
) : ViewModel() {
    private val searchQueryFlow = MutableStateFlow("")
    private var currentSearchJob: Job? = null
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        searchQueryFlow
            .debounce(300L)
            .distinctUntilChanged()
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
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.searchRepositories(query)
                }
            }.onSuccess { repositories ->
                _state.update { it.copy(isLoading = false, repositories = repositories) }
            }.onFailure { e ->
                if (e is CancellationException) throw e
                Napier.e("Search error", e, tag = "Network")
                _state.update {
                    it.copy(
                        isLoading = false,
                        repositories = emptyList(),
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}