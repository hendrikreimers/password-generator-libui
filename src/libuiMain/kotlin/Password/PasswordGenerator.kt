package Password

import Config.GeneratorMode
import Helper.randomChar
import Helper.randomCharAsString
import kotlin.math.ceil
import kotlin.random.Random

/**
 * Helper class that automaticly initiates the requested class and calls the function
 *
 */
class PasswordGenerator(mode: Int = 0) : PasswordGeneratorInterface {
    private lateinit var generator: PasswordGeneratorInterface

    init {
        when ( mode ) {
            GeneratorMode.OPTIMIZED.modeKey ->
                generator = PasswordGeneratorOptimized()
            GeneratorMode.NORMAL.modeKey ->
                generator = PasswordGeneratorNormal()
            GeneratorMode.RANDOM.modeKey ->
                generator = PasswordGeneratorRandom()
        }
    }

    override fun generatePasswords(pwCount: Int, pwLength: Int, specialChars: String, percentOfSpecialChars: Int): List<String> =
        generator.generatePasswords(pwCount, pwLength, specialChars, percentOfSpecialChars)
}

/**
 * Generates passwords which not including small L and big I for readability cases.
 * Also special chars only used in the middle not at start char and end char and
 * only a limited (percent) number of special chars are used.
 *
 */
private class PasswordGeneratorOptimized(): PasswordGeneratorInterface {
    override fun generatePasswords(pwCount: Int, pwLength: Int, specialChars: String, percentOfSpecialChars: Int): List<String> {
        // Char list which only be used as first and last character in password generation
        val pwChars: String = (
            // großes I und kleines L are removed weil sie schwer auseinander zu halten sind
            ('A'..'H') + ('J'..'Z') + ('a'..'k') + ('m'..'z') + (0..9)
        ).joinToString("")

        // Num of chars to remove from length for pre and post chars
        val prePostLen: Int = 2

        // Calculate the count of special chars to be used
        val countOfSpecialChars: Int = ceil((pwLength.toDouble() / 100) * percentOfSpecialChars).toInt()

        // Generates a list of passwords
        return List(pwCount) {
            // Pre and post chars (no special chars)
            val preChar: String  = pwChars.randomCharAsString()
            val postChar: String = pwChars.randomCharAsString()

            // Generate a random string based on the limit of general chars and minimum of special chars
            val charSequence: String = (
                List(pwLength - prePostLen - countOfSpecialChars) {
                    pwChars.randomChar()
                } + List(countOfSpecialChars) {
                    specialChars.randomChar()
                }
            ).shuffled(Random).joinToString("")

            // Put out result pretending the first char and the post char
            preChar + charSequence + postChar
        }
    }
}

/**
 * Generates passwords where special chars only used in the middle not at
 * start char and end char.
 *
 */
private class PasswordGeneratorNormal(): PasswordGeneratorInterface {
    override fun generatePasswords(pwCount: Int, pwLength: Int, specialChars: String, percentOfSpecialChars: Int): List<String> {
        // Char list which only be used as first and last character in password generation
        var pwChars: String = (
            // großes I und kleines L are removed weil sie schwer auseinander zu halten sind
            ('A'..'Z') + ('a'..'z') + (0..9)
        ).joinToString("")

        // Num of chars to remove from length for pre and post chars
        val prePostLen: Int = 2

        // Generates a list of passwords
        return List(pwCount) {
            // Pre and post chars (no special chars)
            val preChar: String = pwChars.randomCharAsString()
            val postChar: String = pwChars.randomCharAsString()

            // Now extend the char list with the special chars
            pwChars += specialChars

            // Generate a random string based on the limit of general chars and minimum of special chars
            val charSequence: String = (
                List(pwLength - prePostLen) {
                    pwChars.randomChar()
                }
            ).shuffled(Random).joinToString("")

            // Put out result pretending the first char and the post char
            preChar + charSequence + postChar
        }
    }
}

/**
 * Alternate password generation with full random, not optimized as before
 *
 */
private class PasswordGeneratorRandom(): PasswordGeneratorInterface {
    override fun generatePasswords(pwCount: Int, pwLength: Int, specialChars: String, percentOfSpecialChars: Int): List<String> {
        // Char list which only be used as first and last character in password generation
        val pwChars: String = (
            ('a'..'z') + ('A'..'Z') + (0..9) + specialChars
        ).joinToString("")

        // Generates a list of passwords
        return List(pwCount) {
            List(pwLength) {
                pwChars.randomChar()
            }.joinToString("")
        }
    }
}
