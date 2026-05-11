package dev.yaseyo.onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yaseyo.design.LocalSnackBarHostState
import dev.yaseyo.design.YaseyoTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AuthScreen(viewModel: AuthViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current

    AuthContent(state = state, eventHandler = viewModel)

    LaunchedEffect(viewModel) {
        viewModel.action.collect {
            snackBarHostState.showSnackbar(it)
        }
    }
}

@Composable
private fun AuthContent(
    state: AuthUiState,
    eventHandler: AuthEventHandler,
) {
    val colors = YaseyoTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(colors.backgroundBase)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppLogo(accentSubtle = Color(colors.accentSubtle))

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Yaseyo",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(colors.accentDefault),
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Cultivating a lighter, healthier you.",
                fontSize = 15.sp,
                color = Color(colors.contentSecondary),
            )

            Spacer(Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(colors.backgroundElevated)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    AuthToggle(
                        selectedTab = state.tab,
                        onTabSelected = eventHandler::onTabChanged,
                    )

                    Spacer(Modifier.height(24.dp))

                    FieldLabel("EMAIL ADDRESS")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = eventHandler::onEmailChanged,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("hello@example.com", color = Color(colors.contentTertiary)) },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = Color(colors.contentTertiary))
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = fieldColors(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    Spacer(Modifier.height(16.dp))

                    FieldLabel("PASSWORD")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = eventHandler::onPasswordChanged,
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = Color(colors.contentTertiary))
                        },
                        trailingIcon = {
                            IconButton(onClick = eventHandler::onPasswordVisibilityToggled) {
                                Icon(
                                    imageVector = if (state.isPasswordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                    contentDescription = if (state.isPasswordVisible) "Hide password" else "Show password",
                                    tint = Color(colors.contentTertiary),
                                )
                            }
                        },
                        visualTransformation = if (state.isPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = fieldColors(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )

                    Spacer(Modifier.height(8.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Text(
                            text = "Forgot Password?",
                            color = Color(colors.accentDefault),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { eventHandler.onForgotPasswordClicked() },
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = eventHandler::onSubmitClicked,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(colors.accentDefault)),
                        enabled = !state.isLoading,
                    ) {
                        Text(
                            text = if (state.tab == AuthTab.SignIn) "Log In →" else "Sign Up →",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(colors.contentOnAccent),
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "By continuing, you agree to Yaseyo's Terms of Service and Privacy Policy.",
                fontSize = 12.sp,
                color = Color(colors.contentTertiary),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun AppLogo(accentSubtle: Color) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(accentSubtle),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "🌿", fontSize = 36.sp)
    }
}

@Composable
private fun AuthToggle(
    selectedTab: AuthTab,
    onTabSelected: (AuthTab) -> Unit,
) {
    val colors = YaseyoTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(Color(colors.backgroundSubtle))
            .padding(4.dp),
    ) {
        AuthTabButton(
            label = "Sign In",
            selected = selectedTab == AuthTab.SignIn,
            modifier = Modifier.weight(1f),
            onClick = { onTabSelected(AuthTab.SignIn) },
        )
        AuthTabButton(
            label = "Sign Up",
            selected = selectedTab == AuthTab.SignUp,
            modifier = Modifier.weight(1f),
            onClick = { onTabSelected(AuthTab.SignUp) },
        )
    }
}

@Composable
private fun AuthTabButton(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = YaseyoTheme.colors
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) Color(colors.accentDefault) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = if (selected) Color(colors.contentOnAccent) else Color(colors.contentSecondary),
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
        )
    }
}

@Composable
private fun FieldLabel(text: String) {
    val colors = YaseyoTheme.colors
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = Color(colors.contentTertiary),
    )
}

@Composable
private fun fieldColors(): TextFieldColors {
    val colors = YaseyoTheme.colors
    return OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color(colors.borderDefault),
        focusedBorderColor = Color(colors.accentDefault),
        unfocusedTextColor = Color(colors.contentPrimary),
        focusedTextColor = Color(colors.contentPrimary),
    )
}

private val previewState = AuthUiState()

private val previewHandler = object : AuthEventHandler {
    override fun onTabChanged(tab: AuthTab) {}

    override fun onEmailChanged(email: String) {}

    override fun onPasswordChanged(password: String) {}

    override fun onPasswordVisibilityToggled() {}

    override fun onSubmitClicked() {}

    override fun onForgotPasswordClicked() {}
}

@Preview(showBackground = true)
@Composable
private fun AuthContentLightPreview() {
    YaseyoTheme(darkTheme = false) { AuthContent(state = previewState, eventHandler = previewHandler) }
}

@Preview(showBackground = true)
@Composable
private fun AuthContentSignUpPreview() {
    YaseyoTheme(darkTheme = false) {
        AuthContent(state = previewState.copy(tab = AuthTab.SignUp), eventHandler = previewHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthContentDarkPreview() {
    YaseyoTheme(darkTheme = true) { AuthContent(state = previewState, eventHandler = previewHandler) }
}
