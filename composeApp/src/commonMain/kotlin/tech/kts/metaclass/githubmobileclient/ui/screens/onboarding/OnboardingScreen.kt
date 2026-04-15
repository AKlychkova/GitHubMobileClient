package tech.kts.metaclass.githubmobileclient.ui.screens.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.Search_octocat
import githubmobileclient.composeapp.generated.resources.onboarding_desc_1
import githubmobileclient.composeapp.generated.resources.onboarding_next_button
import githubmobileclient.composeapp.generated.resources.onboarding_skip_button
import githubmobileclient.composeapp.generated.resources.onboarding_title_1
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tech.kts.metaclass.githubmobileclient.entities.OnboardingPage
import tech.kts.metaclass.githubmobileclient.ui.theme.GitHubMaterialTheme
import tech.kts.metaclass.githubmobileclient.ui.theme.paddingMedium

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel<OnboardingViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                OnboardingUiEvent.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    if (state.pages.isNotEmpty()) {
        OnboardingView(
            pages = state.pages,
            onSkip = viewModel::onSkipClicked,
            onNext = viewModel::onNextClicked,
            modifier = modifier
        )
    }
}

@Composable
private fun OnboardingView(
    pages: List<OnboardingPage>,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        val pagerState = rememberPagerState(pageCount = { pages.size })
        val isLastPage = pagerState.currentPage == pages.lastIndex

        Column(
            modifier = Modifier
                .padding(horizontal = paddingMedium)
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                OnboardingPage(pages[page])
            }

            BottomSection(
                pagerState = pagerState,
                pageCount = pages.size,
                isLastPage = isLastPage,
                onSkip = onSkip,
                onStart = onNext,
            )
        }
    }
}

@Composable
fun BottomSection(
    pagerState: PagerState,
    pageCount: Int,
    isLastPage: Boolean,
    onSkip: () -> Unit,
    onStart: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PageIndicator(
            pageCount = pageCount,
            currentPage = pagerState.currentPage,
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isLastPage) {
            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    text = stringResource(Res.string.onboarding_next_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        } else {
            TextButton(onClick = onSkip) {
                Text(
                    text = stringResource(Res.string.onboarding_skip_button),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier.size(220.dp),
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage

            val width by animateDpAsState(
                targetValue = if (isSelected) 24.dp else 8.dp,
                animationSpec = tween(durationMillis = 300),
            )

            val color by animateColorAsState(
                targetValue = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                },
                animationSpec = tween(durationMillis = 300),
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color),
            )
        }
    }
}

@Composable
@Preview
private fun OnboardingScreenPreview() {
    GitHubMaterialTheme {
        OnboardingView(
            pages = listOf(
                OnboardingPage(
                    image = Res.drawable.Search_octocat,
                    title = Res.string.onboarding_title_1,
                    description = Res.string.onboarding_desc_1
                )
            ),
            onSkip = {},
            onNext = {},
        )
    }
}

@Composable
@Preview
private fun OnboardingScreenPreviewDark() {
    GitHubMaterialTheme(darkTheme = true) {
        OnboardingView(
            pages = listOf(
                OnboardingPage(
                    image = Res.drawable.Search_octocat,
                    title = Res.string.onboarding_title_1,
                    description = Res.string.onboarding_desc_1
                )
            ),
            onSkip = {},
            onNext = {},
        )
    }
}