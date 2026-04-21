package dev.yaseyo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yaseyo.navigation.Home
import dev.yaseyo.navigation.Navigator
import dev.yaseyo.onboarding.api.model.AppState
import dev.yaseyo.onboarding.api.navigation.OnboardingRoutes
import org.koin.android.ext.android.get
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MainActivity :
    ComponentActivity(),
    AndroidScopeComponent {
    override val scope: Scope by activityRetainedScope()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.appState.value == null
        }

        setContent {
            val navigator: Navigator = get()
            val appState by viewModel.appState.collectAsStateWithLifecycle()

            LaunchedEffect(appState) {
                appState?.let {
                    val newRootDestination = when (it) {
                        AppState.FullySetup -> Home

                        AppState.RequiresLogin -> OnboardingRoutes.Auth

                        AppState.RequiresProfileSetup -> OnboardingRoutes.ProfileSetup
                    }

                    navigator.replaceRootDestination(destination = newRootDestination)
                }
            }

            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
