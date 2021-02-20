import Config.Config
import Config.Defaults
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
    // Window not in fullscreen
    fullscreen = false

    // Load configuration from file if possible
    val config: Config = Config("password-generator.ini")

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

            value = config.get("passwordCount").between(1, Defaults.maxPasswordCount).toInt() - 1
        }

        // Input for min/max password length
        label("Password length:")
        pwLenInput = textfield {
            value = config.get("passwordLength").between(Defaults.minPasswordLength, Defaults.maxPasswordLength)

            action {
                // Disable generation button if value is not acceptable
                genBtn.enabled = isStringNumberInRange(value, Defaults.minPasswordLength, Defaults.maxPasswordLength)
            }
        }
        label("(Length must be between 5 and 50)")

        // Additional chars for PW generation
        label("Including special chars:")
        hbox {
            val defaultSpecialChars = uniqueSpecialChars(config.get("specialChars"))

            additionalCharsInput = textfield {
                value = defaultSpecialChars
                stretchy = true

                action {
                    value = uniqueSpecialChars(value)
                }
            }

            button("Reset") {
                action {
                    additionalCharsInput.value = defaultSpecialChars
                }
            }
        }

        // Percentage use of special chars in password generation
        label("Include of special chars:") {
            visible = ( config.get("fullRandom").toInt() <= 0 )
        }
        percentSpecialChars = combobox {
            // Build dropdown item selections
            Defaults.percentSpecialItems(this)
            value = config.get("percentSpecialChars").toInt()
            visible = ( config.get("fullRandom").toInt() <= 0 )
        }

        // Action button
        genBtn = button("Generate passwords") {
            action {
                // Clear text area
                resultTextArea.value = ""

                // Generate list and push output to textbox
                val passwordList: List<String> = if ( config.get("fullRandom").toInt() <= 0 ) generatePasswordList(
                    pwCount               = pwCountDropDown.value + 1,
                    pwLength              = pwLenInput.value.toInt(),
                    specialChars          = additionalCharsInput.value,
                    percentOfSpecialChars = Defaults.getPercentComboValue(percentSpecialChars.value)
                ) else generatePasswordList(
                    pwCount               = pwCountDropDown.value + 1,
                    pwLength              = pwLenInput.value.toInt(),
                    specialChars          = additionalCharsInput.value
                )

                passwordList.forEach { singlePassword ->
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

