package dev.yaseyo.onboarding.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yaseyo.design.LocalSnackBarHostState
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AuthLandingScreen(viewModel: AuthViewModel = koinViewModel()) {
    val snackBarHostState = LocalSnackBarHostState.current

    AuthContent(eventHandler = viewModel)

    LaunchedEffect(viewModel) {
        viewModel.action.collect { snackBarHostState.showSnackbar(it) }
    }
}

@Composable
private fun AuthContent(
    eventHandler: AuthEventHandler,
    modifier: Modifier = Modifier,
) {
    AuthLandingContent(eventHandler = eventHandler, modifier = modifier)
}

@Composable
internal fun AuthLandingContent(
    eventHandler: AuthEventHandler,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AuthHeader()
        AuthOptions(
            onSignInClick = eventHandler::onSignInClicked,
            onSignUpClick = eventHandler::onSignUpClicked,
        )
        TermsText()
    }
}

@Composable
internal fun SheetContent(
    title: String,
    submitLabel: String,
    onSubmit: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = YaseyoTheme.colors.contentPrimary,
        )
        AuthField(label = "Email", state = emailState, placeholder = "yamadataro@yaseyo.com")
        AuthField(label = "Password", state = passwordState, placeholder = "••••••••")
        Button(
            onClick = { onSubmit(emailState.text.toString(), passwordState.text.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = YaseyoTheme.colors.accentDefault,
            ),
        ) {
            Text(
                text = submitLabel,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
private fun AuthHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppLogo(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(YaseyoTheme.colors.accentSubtle),
        )
        Text(
            text = "Yaseyo",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = YaseyoTheme.colors.accentDefault,
        )
        Text(
            text = "Your fat-loss companion",
            fontSize = 14.sp,
            color = YaseyoTheme.colors.contentSecondary,
        )
    }
}

@Composable
private fun AuthOptions(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = onSignUpClick,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = YaseyoTheme.colors.accentDefault,
            ),
        ) {
            Text(
                text = "Get started",
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 16.sp,
            )
        }
        Text(
            text = buildAnnotatedString {
                append("Already have an account? ")
                val linkStart = length
                append("Sign in instead.")
                addLink(
                    clickable = LinkAnnotation.Clickable(
                        tag = "sign_in",
                        linkInteractionListener = { onSignInClick() },
                    ),
                    start = linkStart,
                    end = length,
                )
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            fontSize = 16.sp,
            color = YaseyoTheme.colors.contentPrimary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun TermsText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "By continuing, you agree to Yaseyo's Terms of Service and Privacy Policy.",
        fontSize = 12.sp,
        color = YaseyoTheme.colors.contentTertiary,
    )
}

@Composable
private fun AuthField(
    label: String,
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = YaseyoTheme.colors.contentSecondary,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            placeholder = { Text(text = placeholder, color = YaseyoTheme.colors.contentTertiary) },
            lineLimits = TextFieldLineLimits.SingleLine,
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
    }
}

@Composable
private fun AppLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "🌿", fontSize = 36.sp)
    }
}

internal val previewAuthUiState = AuthUiState()

internal val previewAuthEventHandler = object : AuthEventHandler {
    override fun onSignInClicked() {}

    override fun onSignUpClicked() {}

    override fun onForgotPasswordClicked() {}

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) {}

    override fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun AuthLandingLightPreview() {
    YaseyoPreview(darkTheme = false) {
        AuthLandingContent(eventHandler = previewAuthEventHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthLandingDarkPreview() {
    YaseyoPreview(darkTheme = true) {
        AuthLandingContent(eventHandler = previewAuthEventHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun SheetContentPreview() {
    YaseyoPreview(darkTheme = false) {
        SheetContent(title = "Sign in", submitLabel = "Sign in", onSubmit = { _, _ -> })
    }
}
