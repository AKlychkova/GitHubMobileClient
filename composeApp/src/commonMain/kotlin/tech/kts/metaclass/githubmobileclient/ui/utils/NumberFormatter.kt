package tech.kts.metaclass.githubmobileclient.ui.utils

class NumberFormatter {
    fun format(value: Int): String {
        return when {
            value >= 1_000_000 -> "${round(value / 1_000_000.0)}M"
            value >= 1_000 -> "${round(value / 1_000.0)}k"
            else -> value.toString()
        }
    }

    private fun round(value: Double): String {
        return ((value * 10).toInt() / 10.0)
            .toString()
            .removeSuffix(".0")
    }
}