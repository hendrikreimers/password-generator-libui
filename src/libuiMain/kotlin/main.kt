import libui.ktx.*

/**
 * Simple password generator
 * Made by Hendrik Reimers (CORE23.com | https://github.com/hendrikreimers/)
 *
 * Based on kotlin-libui for kotlin-native usage
 * https://github.com/msink/kotlin-libui/blob/master/docs/libui.ktx/README.md
 * https://github.com/msink/hello-libui
 */
fun main() = appWindow(
    title = "Password Generator",
    width = 320,
    height = 440
) {
    // Window not in fullscreen allowed
    fullscreen = false

    // Some values need to be initialized lately to make them reusable
    lateinit var pwCountDropDown: Combobox
    lateinit var pwLenInput: TextField
    lateinit var additionalCharsInput: TextField
    lateinit var percentSpecialChars: Combobox
    lateinit var genBtn: Button
    lateinit var resultTextArea: TextArea

    vbox {
        // Select for count of passwords
        label("Number of Passwords:")
        pwCountDropDown = combobox {
            // Build dropdown item selections
            for ( i in 1..Defaults.maxPasswordCount ) {
                item(i.toString())
            }

            value = Defaults.passwordCount - 1
        }

        // Input for min/max password length
        label("Password length:")
        pwLenInput = textfield {
            value = Defaults.passwordLength.toString()
            action {
                // Disable generation button if value is not acceptable
                genBtn.enabled = isStringNumberInRange(value, Defaults.minPasswordLength, Defaults.maxPasswordLength)
            }
        }
        label("(Length must be between 5 and 50)")

        // Additional chars for PW generation
        label("Including special chars:")
        hbox {
            additionalCharsInput = textfield {
                value = Defaults.specialChars
                stretchy = true

                action {
                    value = value.toCharArray().distinct().joinToString("") // Unique characters only
                    value = Regex("[0-9a-zA-Z ]+").replace(value, "") // Only special chars and no space
                }
            }

            button("Reset") {
                action {
                    additionalCharsInput.value = Defaults.specialChars
                }
            }
        }

        // Percentage use of special chars in password generation
        label("Include of special chars:")
        percentSpecialChars = combobox {
            // Build dropdown item selections
            Defaults.percentSpecialItems(this)
        }

        // Action button
        genBtn = button("Generate passwords") {
            action {
                // Clear text area
                resultTextArea.value = ""

                // Generate list and push output to textbox
                generatePasswordList(
                    pwCount               = pwCountDropDown.value + 1,
                    pwLength              = pwLenInput.value.toInt(),
                    specialChars          = additionalCharsInput.value,
                    percentOfSpecialChars = Defaults.getPercentComboValue(percentSpecialChars.value)
                ).forEach { singlePassword ->
                    // One password per line
                    resultTextArea.append("""|$singlePassword
                        |
                    """.trimMargin())
                }
            }
        }

        // Read only text area which show the result
        resultTextArea = textarea {
            readonly = true
            stretchy = true
        }

        // Copyright ;-)
        label("Made by Hendrik Reimers (CORE23.com)")
    }
}

