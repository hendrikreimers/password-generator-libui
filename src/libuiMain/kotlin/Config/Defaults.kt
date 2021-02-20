package Config

import libui.ktx.Combobox

// Singleton used for default configuration and default generation
object Defaults {
    const val specialChars: String   = "$%#?=;,:.-_+*&"

    const val passwordCount: Int     = 5  // Chars
    const val passwordLength: Int    = 25 // Chars

    const val maxPasswordCount: Int  = 10 // Number

    const val minPasswordLength: Int = 5  // Chars
    const val maxPasswordLength: Int = 50 // Chars

    const val percentSpecialCharsIndex: Int = 2

    const val fullRandom: Int = 0

    /**
     * Returns the default value for an configuration value
     */
    fun get(key: String): Any {
        return when ( key ) {
            "specialChars"        -> specialChars
            "passwordCount"       -> passwordCount
            "passwordLength"      -> passwordLength
            "maxPasswordCount"    -> maxPasswordCount
            "minPasswordLength"   -> minPasswordLength
            "maxPasswordLength"   -> maxPasswordLength
            "percentSpecialChars" -> percentSpecialCharsIndex
            "fullRandom"         -> fullRandom
            else -> throw Exception("Config key not available in defaults")
        }
    }

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

    fun getComboValueByPercent(percent: Int): Int = when ( percent ) {
        5 -> 1
        15 -> 2
        30 -> 3
        50 -> 4
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
    }
}