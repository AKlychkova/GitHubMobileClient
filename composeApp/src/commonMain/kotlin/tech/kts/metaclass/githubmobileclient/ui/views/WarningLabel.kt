package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.warning
import org.jetbrains.compose.resources.painterResource
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.iconTitleSpace
import tech.kts.metaclass.githubmobileclient.ui.theme.warningIconSize

@Composable
fun WarningLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(iconTitleSpace),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(Res.drawable.warning),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(warningIconSize)
        )
        Text(
            text,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun WarningLabelPreview() {
    GitHubMaterialTheme {
        WarningLabel("Some warning info")
    }
}