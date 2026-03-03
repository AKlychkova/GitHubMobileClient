package tech.kts.metaclass.githubmobileclient.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoriesRepository
import tech.kts.metaclass.githubmobileclient.data.repositories.GitHubRepositoriesRepositoryImpl

class MainViewModel(
    repository: GitHubRepositoriesRepository = GitHubRepositoriesRepositoryImpl() // TODO: вынести в di контейнер
) : ViewModel() {
    private val mutableState = MutableStateFlow(
        MainUiState(
            filteredItems = repository.getRepositories(),
        )
    )
    val state: StateFlow<MainUiState> = mutableState.asStateFlow()
}