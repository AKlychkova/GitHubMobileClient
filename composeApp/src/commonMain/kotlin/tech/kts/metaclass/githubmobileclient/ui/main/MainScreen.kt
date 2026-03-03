package tech.kts.metaclass.githubmobileclient.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.kts.metaclass.githubmobileclient.utils.Dimens

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(Dimens.gapSmall)
    ) {
        items(state.filteredItems) { repository ->
            RepositoryView(repository)
        }
    }
}
