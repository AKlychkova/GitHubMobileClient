package tech.kts.metaclass.githubmobileclient.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage

@Composable
fun LanguageIcon(
    language: ProgrammingLanguage,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = Color(language.color),
                shape = CircleShape
            )
    )
}

@Preview
@Composable
private fun LanguageIconPreview() {
    LanguageIcon(ProgrammingLanguage.KOTLIN)
}