package config

import helper.getKey
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

    private val percentComboValues: Map<Int, Int> = mapOf(
        0 to 0,  // 0 %
        1 to 5,  // 5 %
        2 to 15, // 15 %
        3 to 20, // 20 %
        4 to 25, // 25 %
        5 to 30, // 30 %
        6 to 50  // 50 %
    )

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
     * 2 -> 15, 4 -> 25, ...
     */
    fun getPercentComboValue(selectionValue: Int): Int = when ( selectionValue ) {
        in percentComboValues.keys -> percentComboValues[selectionValue] ?: 0
        else -> 0
    }

    /**
     * Returns the index based on the percent value
     * 15 -> 2, 25 -> 4, ....
     *
     */
    fun getComboValueByPercent(percent: Int): Int = when ( percent ) {
        in percentComboValues.values -> percentComboValues.getKey(percent)
        else -> 0
    }

    /**
     * Sets the default item list for the percent selection usage of special chars
     *
     */
    fun percentSpecialItems(cb: Combobox) {
        cb.apply {
            percentComboValues.forEach { item("${it.value} %") }
        }
    }
}