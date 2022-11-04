package au.com.shiftyjelly.pocketcasts.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.shiftyjelly.pocketcasts.account.AccountAuth
import au.com.shiftyjelly.pocketcasts.account.SignInSource
import au.com.shiftyjelly.pocketcasts.account.onboarding.OnboardingSubmissionState
import au.com.shiftyjelly.pocketcasts.repositories.subscription.SubscriptionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class OnboardingLogInViewModel @Inject constructor(
    private val auth: AccountAuth,
    private val subscriptionManager: SubscriptionManager,
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val _state = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email.trim()) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun logIn(onSuccessfulLogin: () -> Unit) {
        _state.update { it.copy(hasAttemptedLogIn = true) }

        val state = state.value
        if (!state.isEmailValid || !state.isPasswordValid) {
            return
        }

        _state.update {
            it.copy(
                isCallInProgress = true,
                serverErrorMessage = null,
            )
        }

        subscriptionManager.clearCachedStatus()
        viewModelScope.launch {
            val result = auth.signInWithEmailAndPassword(state.email, state.password, SignInSource.Onboarding)
            when (result) {
                is AccountAuth.AuthResult.Success -> {
                    onSuccessfulLogin()
                }

                is AccountAuth.AuthResult.Failed -> {
                    _state.update {
                        it.copy(
                            isCallInProgress = false,
                            serverErrorMessage = result.message,
                        )
                    }
                }
            }
        }
    }
}

data class LogInState(
    val email: String = "",
    val password: String = "",
    val serverErrorMessage: String? = null,
    val isCallInProgress: Boolean = false,
    private val hasAttemptedLogIn: Boolean = false,
) : OnboardingSubmissionState(
    email = email,
    password = password,
    isCallInProgress = isCallInProgress,
    hasAttemptedLogIn = hasAttemptedLogIn,
)