package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.login_failure_dialog_button
import githubmobileclient.composeapp.generated.resources.login_failure_dialog_subtitle
import githubmobileclient.composeapp.generated.resources.login_failure_dialog_title
import org.jetbrains.compose.resources.stringResource
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme

@Composable
fun LoginFailureDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                stringResource(Res.string.login_failure_dialog_title)
            )
        },
        text = {
            Text(
                stringResource(Res.string.login_failure_dialog_subtitle)
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(Res.string.login_failure_dialog_button))
            }
        }
    )
}

@Preview
@Composable
private fun LoginFailureDialogPreview() {
    GitHubMaterialTheme {
        LoginFailureDialog { }
    }
}