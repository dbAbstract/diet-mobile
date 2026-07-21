package dev.yaseyo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yaseyo.navigation.FinishHandler
import dev.yaseyo.navigation.Navigator
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class MainActivity :
    ComponentActivity(),
    AndroidScopeComponent,
    FinishHandler {
    override val scope: Scope by activityRetainedScope()

    private val viewModel: MainViewModel by viewModel()
    private val navigator: Navigator by lazy { scope.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        scope.declare(this as FinishHandler, allowOverride = true)

        splashScreen.setKeepOnScreenCondition {
            viewModel.startDestination.value == null
        }

        setContent {
            val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

            var showApp by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(startDestination) {
                if (!showApp) {
                    startDestination?.let { startDestination ->
                        navigator.goTo(startDestination)
                        showApp = true
                    }
                }
            }

            if (showApp) {
                App(navigator = navigator)
            }
        }
    }
}
