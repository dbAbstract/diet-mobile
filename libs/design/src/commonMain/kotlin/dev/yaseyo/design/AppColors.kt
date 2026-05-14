package dev.yaseyo.design

import androidx.compose.ui.graphics.Color

data class AppColors(
    // ── Backgrounds ──────────────────────────────────────────────────────────
    val backgroundBase: Color,
    val backgroundSubtle: Color,
    val backgroundElevated: Color,
    // ── Content (text & icons) ───────────────────────────────────────────────
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
    val contentDisabled: Color,
    val contentOnAccent: Color,
    // ── Accent (brand action) ────────────────────────────────────────────────
    val accentDefault: Color,
    val accentHover: Color,
    val accentSubtle: Color,
    val accentMuted: Color,
    // ── Borders ───────────────────────────────────────────────────────────────
    val borderSubtle: Color,
    val borderDefault: Color,
    val borderStrong: Color,
    // ── Feedback ──────────────────────────────────────────────────────────────
    val feedbackPositive: Color,
    val feedbackPositiveSubtle: Color,
    val feedbackCritical: Color,
    val feedbackCriticalSubtle: Color,
    val feedbackWarning: Color,
    val feedbackWarningSubtle: Color,
    // ── Scrim ─────────────────────────────────────────────────────────────────
    val scrim: Color,
)

val LightAppColors = AppColors(
    backgroundBase = Color(0xFFFBFDF9),
    backgroundSubtle = Color(0xFFEEF2EF),
    backgroundElevated = Color(0xFFFFFFFF),
    contentPrimary = Color(0xFF191C1A),
    contentSecondary = Color(0xFF404943),
    contentTertiary = Color(0xFF707973),
    contentDisabled = Color(0xFFC0C9C2),
    contentOnAccent = Color(0xFFFFFFFF),
    accentDefault = Color(0xFF1B7A5E),
    accentHover = Color(0xFF145C46),
    accentSubtle = Color(0xFFA5F2D6),
    accentMuted = Color(0xFFDCE5DE),
    borderSubtle = Color(0xFFDCE5DE),
    borderDefault = Color(0xFFC0C9C2),
    borderStrong = Color(0xFF707973),
    feedbackPositive = Color(0xFF1B7A5E),
    feedbackPositiveSubtle = Color(0xFFA5F2D6),
    feedbackCritical = Color(0xFFBA1A1A),
    feedbackCriticalSubtle = Color(0xFFFFDAD6),
    feedbackWarning = Color(0xFF7D5700),
    feedbackWarningSubtle = Color(0xFFFFDFA3),
    scrim = Color(0x99000000),
)

val DarkAppColors = AppColors(
    backgroundBase = Color(0xFF191C1A),
    backgroundSubtle = Color(0xFF1F2421),
    backgroundElevated = Color(0xFF2E3130),
    contentPrimary = Color(0xFFE1E3DF),
    contentSecondary = Color(0xFFC0C9C2),
    contentTertiary = Color(0xFF8A938C),
    contentDisabled = Color(0xFF404943),
    contentOnAccent = Color(0xFF00382A),
    accentDefault = Color(0xFF89D5B5),
    accentHover = Color(0xFFA5E8C9),
    accentSubtle = Color(0xFF005140),
    accentMuted = Color(0xFF404943),
    borderSubtle = Color(0xFF2E3130),
    borderDefault = Color(0xFF404943),
    borderStrong = Color(0xFF8A938C),
    feedbackPositive = Color(0xFF89D5B5),
    feedbackPositiveSubtle = Color(0xFF005140),
    feedbackCritical = Color(0xFFFFB4AB),
    feedbackCriticalSubtle = Color(0xFF93000A),
    feedbackWarning = Color(0xFFF5BE48),
    feedbackWarningSubtle = Color(0xFF4C3800),
    scrim = Color(0xCC000000),
)
