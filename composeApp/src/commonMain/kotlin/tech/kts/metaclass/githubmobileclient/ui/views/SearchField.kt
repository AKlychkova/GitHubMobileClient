package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.search_field_clear_button_description
import githubmobileclient.composeapp.generated.resources.search_field_hint
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchField(
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
            colors = tertiaryTextFieldColors(),
            placeholder = {
                Text(
                    text = stringResource(Res.string.search_field_hint),
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
                            contentDescription = stringResource(Res.string.search_field_clear_button_description),
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