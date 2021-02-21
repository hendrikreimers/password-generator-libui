package Helper

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * Returns value which is between min and max
 *
 */
fun String.between(min: Int, max: Int): String = this.toInt().between(min,max).toString()
fun Int.between(min: Int, max: Int): Int = max(min, min(this, max))

/**
 * Returns a random char of string
 *
 */
fun String.randomChar(): Char = this[Random.nextInt(0, this.length)]
fun String.randomCharAsString(): String = this.randomChar().toString()

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