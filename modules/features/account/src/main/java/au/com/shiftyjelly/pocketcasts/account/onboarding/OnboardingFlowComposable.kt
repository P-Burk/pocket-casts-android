package au.com.shiftyjelly.pocketcasts.account.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme

@Composable
fun OnboardingFlowComposable(
    activeTheme: Theme.ThemeType,
    completeOnboarding: () -> Unit,
) {
    AppThemeWithBackground(activeTheme) {
        val navController = rememberNavController()

        // If the BackHandler is disabled, then the normal back pressed behavior
        // takes over and closes the app
        val enableBackHandler = navController.backQueue.isNotEmpty()
        BackHandler(enabled = enableBackHandler) {
            navController.popBackStack()
        }

        NavHost(
            navController = navController,
            startDestination = OnboardingNavRoute.logInOrSignUp
        ) {
            composable(OnboardingNavRoute.logInOrSignUp) {
                OnboardingLoginOrSignUpPage(
                    onNotNowClicked = completeOnboarding,
                    onSignUpFreeClicked = { navController.navigate(OnboardingNavRoute.createFreeAccount) },
                    onLoginClicked = { navController.navigate(OnboardingNavRoute.logIn) },
                    onLoginGoogleClicked = { navController.navigate(OnboardingNavRoute.logInGoogle) }
                )
            }

            composable(OnboardingNavRoute.createFreeAccount) {
                OnboardingCreateFreeAccountPage(onBackPressed = { navController.popBackStack() })
            }

            composable(OnboardingNavRoute.logIn) {
                OnboardingLoginPage(
                    onBackPressed = { navController.popBackStack() },
                    onLoginComplete = { completeOnboarding() },
                    onForgotPasswordTapped = { navController.navigate(OnboardingNavRoute.forgotPassword) },
                )
            }

            composable(OnboardingNavRoute.logInGoogle) {
                OnboardingLoginGooglePage()
            }

            composable(OnboardingNavRoute.forgotPassword) {
                OnboardingForgotPassword(onBackPressed = { navController.popBackStack() })
            }
        }
    }
}

private object OnboardingNavRoute {
    const val logInOrSignUp = "log_in_or_sign_up"
    const val createFreeAccount = "create_free_account"
    const val logIn = "log_in"
    const val logInGoogle = "log_in_google"
    const val forgotPassword = "forgot_password"
}
