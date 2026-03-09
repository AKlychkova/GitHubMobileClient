package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.Owner
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.padding
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryView

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainView(state)
}

@Composable
private fun MainView(state: MainUiState) {
    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = padding)
                .padding(contentPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(gapSmall)
        ) {
            items(state.filteredItems) { repository ->
                RepositoryView(
                    repository = repository,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() {
    val repositoryMock = GitHubRepository(
        name = "My first repository",
        description = "Test project",
        language = ProgrammingLanguage.KOTLIN,
        stars = 5,
        owner = Owner(
            username = "AKlychkova",
            avatarUrl = "https://avatars.githubusercontent.com/u/90353866?v=4"
        )
    )
    val repositories = List(10) { repositoryMock }
    var state by remember { mutableStateOf(MainUiState(repositories)) }

    GitHubMaterialTheme {
        MainView(state)
    }
}

@Composable
@Preview
private fun MainScreenPreviewDark() {
    val repositoryMock = GitHubRepository(
        name = "My first repository",
        description = "Test project",
        language = ProgrammingLanguage.KOTLIN,
        stars = 5,
        owner = Owner(
            username = "AKlychkova",
            avatarUrl = "https://avatars.githubusercontent.com/u/90353866?v=4"
        )
    )
    val repositories = List(10) { repositoryMock }
    var state by remember { mutableStateOf(MainUiState(repositories)) }

    GitHubMaterialTheme(darkTheme = true) {
        MainView(state)
    }
}
