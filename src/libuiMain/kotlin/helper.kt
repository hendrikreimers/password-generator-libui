import kotlin.math.max
import kotlin.math.min

/**
 * Returns value which is between min and max
 *
 */
fun String.between(min: Int, max: Int): String = this.toInt().between(min,max).toString()
fun Int.between(min: Int, max: Int): Int = max(min, min(this, max))

/**
 * Makes each char in the string unique and removes any others like space or A-Z and 0-9
 *
 */
fun uniqueSpecialChars(value: String): String {
    var result = value.toCharArray().distinct().joinToString("") // Unique characters only
    result = Regex("[0-9a-zA-Z ]+").replace(result, "") // Only special chars and no space

    return result
}

/**
 * Checks if a string includes only a number
 * and the number is inside given range
 *
 */
fun isStringNumberInRange(value: String, min: Int,max: Int): Boolean {
    if ( Regex("^[0-9]+$").matchEntire(value) !== null ) {
        val intValue: Int = value.toInt();

        if ( intValue >= min && intValue <= max)
            return true;
    }

    return false;
}
