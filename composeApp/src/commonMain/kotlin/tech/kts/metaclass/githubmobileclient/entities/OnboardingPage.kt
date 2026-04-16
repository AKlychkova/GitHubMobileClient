package tech.kts.metaclass.githubmobileclient.entities

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class OnboardingPage(
    val image: DrawableResource,
    val title: StringResource,
    val description: StringResource,
)
