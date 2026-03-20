package tech.kts.metaclass.githubmobileclient.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.main_clear_button_description
import githubmobileclient.composeapp.generated.resources.main_search_field_hint
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.entities.GitHubRepository
import tech.kts.metaclass.githubmobileclient.entities.User
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingSmall
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryShimmer
import tech.kts.metaclass.githubmobileclient.ui.views.RepositoryView

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() },
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainView(
        state = state,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearSearch = viewModel::clearSearch,
        modifier = modifier
    )
}

@Composable
private fun MainView(
    state: MainUiState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Column (
            modifier = Modifier
                .padding(horizontal = paddingMedium)
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            SearchField(
                searchQuery = state.searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onClearSearch = onClearSearch,
                enabled = !state.isLoading,
                modifier = Modifier
                    .padding(vertical = paddingSmall)
                    .fillMaxWidth()
            )
            if (state.isLoading) {
                Shimmers()
            } else {
                Repositories(state.repositories)
            }
        }
    }
}

@Composable
private fun Repositories(
    repositoriesList: List<GitHubRepository>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(gapSmall),
        modifier = modifier
    ) {
        items(
            items = repositoriesList,
            key = { it.id }
        ) { repository ->
            RepositoryView(
                repository = repository,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Shimmers(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(gapSmall),
        modifier = modifier
    ) {
        repeat(5) {
            RepositoryShimmer(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SearchField(
    searchQuery: String,
    enabled: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = searchQuery,
            enabled = enabled,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(Res.string.main_search_field_hint),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = onClearSearch) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(Res.string.main_clear_button_description),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )
    }
}

@Composable
@Preview
private fun MainScreenPreview(
    @PreviewParameter(MainScreenPreviewProvider::class) darkTheme: Boolean
) {
    val repositoryMock = GitHubRepository(
        id = 0,
        name = "My first repository",
        description = "Test project",
        language = ProgrammingLanguage.KOTLIN,
        stars = 5,
        owner = User(
            id = 1L,
            username = "AKlychkova",
            avatarUrl = "https://avatars.githubusercontent.com/u/90353866?v=4"
        )
    )
    val repositories = List(10) { id -> repositoryMock.copy(id = id) }
    var state by remember { mutableStateOf(MainUiState(repositories = repositories)) }

    GitHubMaterialTheme(darkTheme = darkTheme) {
        MainView(
            state = state,
            onSearchQueryChange = {},
            onClearSearch = {},
        )
    }
}

class MainScreenPreviewProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(false, true)
}
