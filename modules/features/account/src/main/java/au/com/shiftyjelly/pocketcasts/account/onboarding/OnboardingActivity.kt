package au.com.shiftyjelly.pocketcasts.account.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import au.com.shiftyjelly.pocketcasts.account.onboarding.OnboardingActivityContract.OnboardingFinish
import au.com.shiftyjelly.pocketcasts.analytics.AnalyticsTrackerWrapper
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    @Inject lateinit var theme: Theme
    @Inject lateinit var analyticsTracker: AnalyticsTrackerWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingFlowComposable(
                activeTheme = theme.activeTheme,
                completeOnboarding = {
                    finishWithResult(OnboardingFinish.CompletedOnboarding)
                },
                abortOnboarding = {
                    finishWithResult(OnboardingFinish.AbortedOnboarding)
                },
                analyticsTracker = analyticsTracker
            )
        }
    }

    private fun finishWithResult(result: OnboardingFinish) {
        setResult(
            Activity.RESULT_OK,
            Intent().apply {
                putExtra(OnboardingActivityContract.FINISH_KEY, result)
            }
        )
        finish()
    }
}
