package Config

import libui.ktx.Combobox

// Helps to generate the options and convert the configuration to the right key
enum class GeneratorMode(val modeKey: Int) {
    OPTIMIZED(0),
    NORMAL(1),
    RANDOM(2)
}

// Singleton used for default configuration and default generation
object Defaults {
    const val specialChars: String          = "$%#?=;,:.-_+*&!~"

    const val passwordCount: Int            = 5  // Chars
    const val passwordLength: Int           = 25 // Chars

    const val maxPasswordCount: Int         = 10 // Number

    const val minPasswordLength: Int        = 5  // Chars
    const val maxPasswordLength: Int        = 50 // Chars

    const val percentSpecialCharsIndex: Int = 2

    private val generatorMode: Int          = GeneratorMode.OPTIMIZED.modeKey

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
            "mode"                -> generatorMode
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
        3 -> 20
        4 -> 25
        5 -> 30
        6 -> 50
        else -> 0
    }

    /**
     * Returns the index based on the percent value
     *
     */
    fun getComboValueByPercent(percent: Int): Int = when ( percent ) {
        5 -> 1
        15 -> 2
        20 -> 3
        25 -> 4
        30 -> 5
        50 -> 6
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
            item("20 %")
            item("25 %")
            item("30 %")
            item("50 %")
        }
    }
}