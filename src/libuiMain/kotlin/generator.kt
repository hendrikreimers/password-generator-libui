import Config.GeneratorMode
import kotlin.math.ceil
import kotlin.random.Random

/**
 * Helper function to call the correct function
 *
 */
fun generatePasswordList(
    pwCount: Int = 5,
    pwLength: Int = 15,
    specialChars: String = "$%#?=;,:.-_+*&",
    percentOfSpecialChars: Int = 30,
    mode: Int = 0
): List<String> {
    return when ( mode ) {
        GeneratorMode.OPTIMIZED.modeKey ->
            generatePasswordListOptimized(pwCount, pwLength, specialChars, percentOfSpecialChars)
        GeneratorMode.NORMAL.modeKey ->
            generatePasswordListNormal(pwCount, pwLength, specialChars)
        GeneratorMode.RANDOM.modeKey ->
            generatePasswordListRandom(pwCount, pwLength, specialChars)
        else -> listOf("")
    }
}

/**
 * Generates passwords which not including small L and big I for readability cases.
 * Also special chars only used in the middle not at start char and end char and
 * only a limited (percent) number of special chars are used.
 *
 */
fun generatePasswordListOptimized(
    pwCount: Int = 5,
    pwLength: Int = 15,
    specialChars: String = "$%#?=;,:.-_+*&",
    percentOfSpecialChars: Int = 30
): List<String> {
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
        val preChar: String = pwChars[Random.nextInt(0, pwChars.length)].toString()
        val postChar: String = pwChars[Random.nextInt(0, pwChars.length)].toString()

        // Generate a random string based on the limit of general chars and minimum of special chars
        val charSequence: String = (
            List(pwLength - prePostLen - countOfSpecialChars) {
                pwChars[Random.nextInt(0, pwChars.length)]
            } + List(countOfSpecialChars) {
                specialChars[Random.nextInt(0, specialChars.length)]
            }
        ).shuffled(Random).joinToString("")

        // Put out result pretending the first char and the post char
        preChar + charSequence + postChar
    }
}

/**
 * Generates passwords where special chars only used in the middle not at
 * start char and end char.
 *
 */
fun generatePasswordListNormal(
    pwCount: Int = 5,
    pwLength: Int = 15,
    specialChars: String = "$%#?=;,:.-_+*&"
): List<String> {
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
        val preChar: String = pwChars[Random.nextInt(0, pwChars.length)].toString()
        val postChar: String = pwChars[Random.nextInt(0, pwChars.length)].toString()

        // Now extend the char list with the special chars
        pwChars += specialChars

        // Generate a random string based on the limit of general chars and minimum of special chars
        val charSequence: String = (
            List(pwLength - prePostLen) {
                pwChars[Random.nextInt(0, pwChars.length)]
            }
        ).shuffled(Random).joinToString("")

        // Put out result pretending the first char and the post char
        preChar + charSequence + postChar
    }
}

/**
 * Alternate password generation with full random, not optimized as before
 *
 */
fun generatePasswordListRandom(
    pwCount: Int = 5,
    pwLength: Int = 15,
    specialChars: String = "$%#?=;,:.-_+*&",
): List<String> {
    // Char list which only be used as first and last character in password generation
    val pwChars: String = (
            ('a'..'z') + ('A'..'Z') + (0..9) + specialChars
            ).joinToString("")

    // Generates a list of passwords
    return List(pwCount) {
        List(pwLength) {
            pwChars[Random.nextInt(0, pwChars.length)]
        }.joinToString("")
    }
}