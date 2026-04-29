package dev.yaseyo.design

data class AppColors(
    // ── Backgrounds ──────────────────────────────────────────────────────────
    val backgroundBase: Long, // app root background
    val backgroundSubtle: Long, // recessed areas, input fills
    val backgroundElevated: Long, // cards, sheets, dialogs
    // ── Content (text & icons) ───────────────────────────────────────────────
    val contentPrimary: Long, // body text, primary icons
    val contentSecondary: Long, // labels, captions
    val contentTertiary: Long, // placeholder, hints
    val contentDisabled: Long, // disabled state
    val contentOnAccent: Long, // text / icons sitting on accent fill
    // ── Accent (brand action) ────────────────────────────────────────────────
    val accentDefault: Long, // buttons, active controls, links
    val accentHover: Long, // accentDefault darkened for hover/press
    val accentSubtle: Long, // tinted backgrounds, selected rows
    val accentMuted: Long, // very light tint, chips, tags
    // ── Borders ───────────────────────────────────────────────────────────────
    val borderSubtle: Long, // dividers, hairlines
    val borderDefault: Long, // input rings, card edges
    val borderStrong: Long, // focused input, emphasis
    // ── Feedback ──────────────────────────────────────────────────────────────
    val feedbackPositive: Long,
    val feedbackPositiveSubtle: Long,
    val feedbackCritical: Long,
    val feedbackCriticalSubtle: Long,
    val feedbackWarning: Long,
    val feedbackWarningSubtle: Long,
    // ── Scrim ─────────────────────────────────────────────────────────────────
    val scrim: Long,
)

val LightAppColors = AppColors(
    backgroundBase = 0xFFFBFDF9L,
    backgroundSubtle = 0xFFEEF2EFL,
    backgroundElevated = 0xFFFFFFFFL,
    contentPrimary = 0xFF191C1AL,
    contentSecondary = 0xFF404943L,
    contentTertiary = 0xFF707973L,
    contentDisabled = 0xFFC0C9C2L,
    contentOnAccent = 0xFFFFFFFFL,
    accentDefault = 0xFF1B7A5EL,
    accentHover = 0xFF145C46L,
    accentSubtle = 0xFFA5F2D6L,
    accentMuted = 0xFFDCE5DEL,
    borderSubtle = 0xFFDCE5DEL,
    borderDefault = 0xFFC0C9C2L,
    borderStrong = 0xFF707973L,
    feedbackPositive = 0xFF1B7A5EL,
    feedbackPositiveSubtle = 0xFFA5F2D6L,
    feedbackCritical = 0xFFBA1A1AL,
    feedbackCriticalSubtle = 0xFFFFDAD6L,
    feedbackWarning = 0xFF7D5700L,
    feedbackWarningSubtle = 0xFFFFDFA3L,
    scrim = 0x99000000L,
)

val DarkAppColors = AppColors(
    backgroundBase = 0xFF191C1AL,
    backgroundSubtle = 0xFF1F2421L,
    backgroundElevated = 0xFF2E3130L,
    contentPrimary = 0xFFE1E3DFL,
    contentSecondary = 0xFFC0C9C2L,
    contentTertiary = 0xFF8A938CL,
    contentDisabled = 0xFF404943L,
    contentOnAccent = 0xFF00382AL,
    accentDefault = 0xFF89D5B5L,
    accentHover = 0xFFA5E8C9L,
    accentSubtle = 0xFF005140L,
    accentMuted = 0xFF404943L,
    borderSubtle = 0xFF2E3130L,
    borderDefault = 0xFF404943L,
    borderStrong = 0xFF8A938CL,
    feedbackPositive = 0xFF89D5B5L,
    feedbackPositiveSubtle = 0xFF005140L,
    feedbackCritical = 0xFFFFB4ABL,
    feedbackCriticalSubtle = 0xFF93000AL,
    feedbackWarning = 0xFFF5BE48L,
    feedbackWarningSubtle = 0xFF4C3800L,
    scrim = 0xCC000000L,
)
