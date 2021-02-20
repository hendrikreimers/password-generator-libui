import libui.ktx.Combobox

// Singleton used for default configuration and default generation
object Defaults {
    val specialChars: String   = "$%#?=;,:.-_+*&"
    val passwordCount: Int     = 5  // Chars
    val passwordLength: Int    = 25 // Chars

    val maxPasswordCount: Int  = 10 // Number

    val minPasswordLength: Int = 5  // Chars
    val maxPasswordLength: Int = 50 // Chars

    /**
     * Transforms the index of the combobox selection to the real result
     *
     */
    fun getPercentComboValue(selectionValue: Int): Int = when ( selectionValue ) {
        1 -> 5
        2 -> 15
        3 -> 30
        4 -> 50
        else -> 0
    }

    /**
     * Sets the default item list for the percent selection usage of special chars
     *
     */
    fun percentSpecialItems(cb: Combobox) {
        cb.apply {
            item("0 %")
            item("5 %")
            item("15 %")
            item("30 %")
            item("50 %")
        }

        cb.value = 2 // Pre select 15% as default
    }
}