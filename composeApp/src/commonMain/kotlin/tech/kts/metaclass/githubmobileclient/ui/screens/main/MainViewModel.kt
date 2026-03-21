package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoriesRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoriesRepositoryImpl

class MainViewModel(
    private val repository: GitHubRepositoriesRepository = GitHubRepositoriesRepositoryImpl() // TODO: вынести в di контейнер
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(filteredItems = repository.getRepositories())
            }
        }
    }
}