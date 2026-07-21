package dev.yaseyo.onboarding.ui.profilesetup.widgets

internal fun Double.format1dp(): String {
    val scaled = (this * 10).toLong()
    return "${scaled / 10}.${kotlin.math.abs(scaled % 10)}"
}
