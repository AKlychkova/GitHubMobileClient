package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.chipBorderWidth
import tech.kts.metaclass.githubmobileclient.ui.theme.chipPadding

@Composable
fun InfoChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            chipBorderWidth,
            MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(chipPadding)
        )
    }
}

@Preview
@Composable
private fun InfoChipPreview() {
    GitHubMaterialTheme {
        InfoChip(text = "Some text")
    }
}