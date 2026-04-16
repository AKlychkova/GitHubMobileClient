package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable

@Composable
fun tertiaryTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
    cursorColor = MaterialTheme.colorScheme.tertiary,
    focusedLabelColor = MaterialTheme.colorScheme.tertiary
)