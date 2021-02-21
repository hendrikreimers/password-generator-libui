/**
 * Makes each char in the string unique and removes any others like space or A-Z and 0-9
 *
 */
fun uniqueSpecialChars(value: String): String {
    val result = value.toCharArray().distinct().joinToString("") // Unique characters only
    return Regex("[0-9a-zA-Z ]+").replace(result, "") // Only special chars and no space
}