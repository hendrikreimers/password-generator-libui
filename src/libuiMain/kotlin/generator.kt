import kotlin.math.ceil
import kotlin.random.Random

/**
 * Generates a list of passwords
 * and takes care that no special char is first or last character
 *
 */
fun generatePasswordList(
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
    val passwordList: List<String> = List(pwCount) {
        // Pre and post chars (no special chars)
        val preChar: String  = pwChars[ Random.nextInt(0, pwChars.length) ].toString()
        val postChar: String = pwChars[ Random.nextInt(0, pwChars.length) ].toString()

        // Generate a random string based on the limit of general chars and minimum of special chars
        val charSequence: String = (
                List(pwLength - prePostLen - countOfSpecialChars) {
                    pwChars[ Random.nextInt(0, pwChars.length) ]
                } + List(countOfSpecialChars) {
                    specialChars[ Random.nextInt(0, specialChars.length) ]
                }
                ).shuffled(Random).joinToString("")

        // Put out result pretending the first char and the post char
        preChar + charSequence + postChar
    }

    return passwordList
}

/**
 * Alternate password generation with full random option
 *
 */
fun generatePasswordList(
    pwCount: Int = 5,
    pwLength: Int = 15,
    specialChars: String = "$%#?=;,:.-_+*&",
): List<String> {
    // Char list which only be used as first and last character in password generation
    val pwChars: String = (
            ('a'..'z') + ('A'..'Z') + (0..9) + specialChars
            ).joinToString("")

    // Generates a list of passwords
    val passwordList: List<String> = List(pwCount) {
        List(pwLength) {
            pwChars[ Random.nextInt(0, pwChars.length) ]
        }.joinToString("")
    }

    return passwordList
}