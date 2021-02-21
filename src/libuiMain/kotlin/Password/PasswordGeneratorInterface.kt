package Password

/**
 * Just an interface
 *
 */
interface PasswordGeneratorInterface {
    fun generatePasswords(pwCount: Int, pwLength: Int, specialChars: String, percentOfSpecialChars: Int): List<String>
}