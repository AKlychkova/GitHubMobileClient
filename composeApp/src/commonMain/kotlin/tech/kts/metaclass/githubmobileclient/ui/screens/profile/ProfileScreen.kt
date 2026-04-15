package tech.kts.metaclass.githubmobileclient.ui.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import githubmobileclient.composeapp.generated.resources.location
import githubmobileclient.composeapp.generated.resources.mail
import githubmobileclient.composeapp.generated.resources.organisation
import githubmobileclient.composeapp.generated.resources.people
import githubmobileclient.composeapp.generated.resources.repo_small
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.profile_followers
import githubmobileclient.composeapp.generated.resources.profile_logout_button
import githubmobileclient.composeapp.generated.resources.profile_repositories
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.gapMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.gapSmall
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium
import tech.kts.metaclass.githubmobileclient.ui.theme.profileAvatarSize
import tech.kts.metaclass.githubmobileclient.ui.theme.profileIconSize
import tech.kts.metaclass.githubmobileclient.ui.theme.profileIconTitleSpace
import tech.kts.metaclass.githubmobileclient.ui.views.Avatar
import tech.kts.metaclass.githubmobileclient.ui.views.Error
import tech.kts.metaclass.githubmobileclient.ui.views.Shimmer

@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileView(
        state = state,
        onRetry = viewModel::onRetry,
        onLogout = {
            viewModel.onLogout()
            onNavigateToLogin()
        },
        modifier = modifier
    )
}

@Composable
private fun ProfileView(
    state: ProfileScreenUiState,
    onRetry: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = paddingMedium)
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is ProfileScreenUiState.Error -> {
                    Error(
                        onRetryClick = onRetry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    LogoutButton(onLogout)
                }

                is ProfileScreenUiState.Loading -> {
                    ProfileSkeleton(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is ProfileScreenUiState.Success -> {
                    Profile(
                        profile = state.profile,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    LogoutButton(onLogout)
                }
            }
        }
    }
}

@Composable
private fun Profile(
    profile: ProfileUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Avatar(
            url = profile.avatarUrl,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(paddingMedium)
                .size(profileAvatarSize)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                )
        )
        Text(
            text = profile.name ?: profile.login,
            style = MaterialTheme.typography.headlineMedium
        )
        if (profile.name != null) {
            Text(
                text = profile.login,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Thin
            )
        }
        Spacer(Modifier.height(gapMedium))
        if (profile.bio != null) {
            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(gapMedium))
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(gapSmall)
        ) {
            Followers(profile.followers)
            Organization(profile.company)
            Location(profile.location)
            Email(profile.email)
            RepositoryCount(profile.repositoriesCount)
        }
    }
}

@Composable
private fun Followers(followers: String) {
    if (followers != "0") {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(profileIconTitleSpace)
        ) {
            Icon(
                painter = painterResource(Res.drawable.people),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(profileIconSize)
            )
            Text(
                text = stringResource(Res.string.profile_followers, followers),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun Organization(organisation: String?) {
    if (organisation != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(profileIconTitleSpace)
        ) {
            Icon(
                painter = painterResource(Res.drawable.organisation),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(profileIconSize)
            )
            Text(
                text = organisation,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun Location(location: String?) {
    if (location != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(profileIconTitleSpace)
        ) {
            Icon(
                painter = painterResource(Res.drawable.location),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(profileIconSize)
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun Email(email: String?) {
    if (email != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(profileIconTitleSpace)
        ) {
            Icon(
                painter = painterResource(Res.drawable.mail),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(profileIconSize)
            )
            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun RepositoryCount(count: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(profileIconTitleSpace)
    ) {
        Icon(
            painter = painterResource(Res.drawable.repo_small),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(profileIconSize)
        )
        Text(
            text = stringResource(Res.string.profile_repositories, count),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun LogoutButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    ) {
        Text(stringResource(Res.string.profile_logout_button))
    }
    Spacer(modifier = Modifier.height(gapSmall))
}

@Composable
private fun ProfileSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Shimmer(
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(paddingMedium)
                .size(profileAvatarSize)
        )
        Shimmer(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(.8f)
                .height(36.dp)
        )
        Spacer(Modifier.height(8.dp))
        Shimmer(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(.4f)
                .height(32.dp)
        )
        Spacer(Modifier.height(gapMedium))
        Column(
            verticalArrangement = Arrangement.spacedBy(gapSmall)
        ) {
            repeat(3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(profileIconTitleSpace)
                ) {
                    Shimmer(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.size(profileIconSize)
                    )
                    Shimmer(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .height(profileIconSize)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    val profileMock = ProfileUiState(
        login = "OctoCat",
        name = "Monalisa Octocat",
        bio = "There once was...",
        location = "San Francisco",
        followers = "20",
        company = "GitHub",
        email = "octocat@github.com",
        avatarUrl = "https://avatars.githubusercontent.com/u/14364638?s=48&v=4",
        repositoriesCount = "32"
    )
    GitHubMaterialTheme {
        ProfileView(
            state = ProfileScreenUiState.Success(profileMock),
            onRetry = {},
            onLogout = {}
        )
    }
}

@Preview
@Composable
private fun ProfileScreenLoadingPreview() {
    GitHubMaterialTheme {
        ProfileView(
            state = ProfileScreenUiState.Loading,
            onRetry = {},
            onLogout = {}
        )
    }
}