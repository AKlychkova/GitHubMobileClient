package tech.kts.metaclass.githubmobileclient.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.CompleteOnboardingUseCase
import tech.kts.metaclass.githubmobileclient.useCases.onboarding.GetOnboardingPagesUseCase

class OnboardingViewModel(
    private val completeOnboarding: CompleteOnboardingUseCase,
    private val getPages: GetOnboardingPagesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<OnboardingUiEvent>()
    val events: SharedFlow<OnboardingUiEvent> = _events.asSharedFlow()

    init {
        _state.update { OnboardingUiState(getPages()) }
    }

    fun onSkipClicked() {
        finishOnboarding()
    }

    fun onNextClicked() {
        finishOnboarding()
    }

    private fun finishOnboarding() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                completeOnboarding()
            }
            pushEvent(OnboardingUiEvent.NavigateToLogin)
        }
    }

    private fun pushEvent(event: OnboardingUiEvent) = viewModelScope.launch {
        _events.emit(event)
    }
}