package dev.yaseyo.onboarding.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yaseyo.design.LocalSnackBarHostState
import dev.yaseyo.design.YaseyoPreview
import dev.yaseyo.design.YaseyoTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SignUpSheetScreen(viewModel: AuthViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current

    SignUpSheetContent(state = state, eventHandler = viewModel)

    LaunchedEffect(viewModel) {
        viewModel.action.collect { snackBarHostState.showSnackbar(it) }
    }
}

@Composable
internal fun SignUpSheetContent(
    state: AuthUiState,
    eventHandler: AuthEventHandler,
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
            text = "Create account",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = YaseyoTheme.colors.contentPrimary,
        )

        SheetEmailField(state = emailState)

        SheetPasswordField(state = passwordState)

        Button(
            onClick = {
                eventHandler.signUpWithEmailAndPassword(
                    email = emailState.text.toString(),
                    password = passwordState.text.toString(),
                )
            },
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = YaseyoTheme.colors.accentDefault,
            ),
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = "Create account",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

// --- Private field components ---

@Composable
private fun SheetEmailField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "Email",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = YaseyoTheme.colors.contentSecondary,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            placeholder = {
                Text(text = "yamadataro@yaseyo.com", color = YaseyoTheme.colors.contentTertiary)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
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
private fun SheetPasswordField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "Password",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = YaseyoTheme.colors.contentSecondary,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            placeholder = {
                Text(text = "••••••••", color = YaseyoTheme.colors.contentTertiary)
            },
            outputTransformation = if (!isVisible) PasswordMaskTransformation else null,
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        imageVector = if (isVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (isVisible) "Hide password" else "Show password",
                        tint = YaseyoTheme.colors.contentSecondary,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
    }
}

private val PasswordMaskTransformation = OutputTransformation {
    if (length > 0) replace(0, length, "•".repeat(length))
}

@Preview(showBackground = true)
@Composable
private fun SignUpSheetLightPreview() {
    YaseyoPreview(darkTheme = false) {
        SignUpSheetContent(state = previewAuthUiState, eventHandler = previewAuthEventHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpSheetDarkPreview() {
    YaseyoPreview(darkTheme = true) {
        SignUpSheetContent(state = previewAuthUiState, eventHandler = previewAuthEventHandler)
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpSheetLoadingPreview() {
    YaseyoPreview(darkTheme = false) {
        SignUpSheetContent(state = previewAuthUiState, eventHandler = previewAuthEventHandler)
    }
}
