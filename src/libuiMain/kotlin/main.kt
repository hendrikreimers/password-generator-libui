import config.Config
import config.Defaults
import config.GeneratorMode
import helper.between
import helper.isStringNumberInRange
import password.PasswordGenerator
import windows.control.toClipboard
import kotlinx.cinterop.memScoped
import libui.ktx.*
import libui.uiQuit
import windows.SimpleWindow
import windows.ui.aboutWindow

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
    height = 540
) {
    // Window not in fullscreen
    fullscreen = false

    // Load configuration from file if possible
    val config: Config = Config("password-generator.ini")

    // Some values need to be initialized lately to make them reusable
    lateinit var pwCountDropDown: Combobox
    lateinit var pwLenInput: TextField
    lateinit var additionalCharsInput: TextField
    lateinit var mode: Combobox
    lateinit var percentSpecialChars: Combobox
    lateinit var genBtn: Button
    lateinit var resultTextArea: TextArea

    // Define the available dialogs
    val dialogs: Map<String, SimpleWindow> = mapOf(
        "about" to aboutWindow()
    )

    // Set App closing handling to clear all resources
    onClose {
        dialogs.values.forEach { it.dispose() }
        uiQuit()
        true
    }
    onShouldQuit {
        dialogs.values.forEach { it.hide() }
        dispose()
        true
    }

    /**
     * The View
     *
     */
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

        // Rendering mode selection
        label("Render mode:")
        mode = combobox {
            for ( value in GeneratorMode.values() )
                item(value.name.toLowerCase().capitalize())

            value = config.get("mode").toInt()

            action {
                percentSpecialChars.enabled = ( value == GeneratorMode.OPTIMIZED.modeKey )
            }
        }

        // Percentage use of special chars in password generation
        label("Include of special chars:")
        percentSpecialChars = combobox {
            // Build dropdown item selections
            Defaults.percentSpecialItems(this)

            value = config.get("percentSpecialChars").toInt()
            enabled = ( config.get("mode").toInt() == GeneratorMode.OPTIMIZED.modeKey )
        }

        // Action button
        hbox {
            genBtn = button("Generate passwords") {
                stretchy = true

                action {
                    // Clear text area
                    resultTextArea.value = ""

                    // Generate list and push output to textbox
                    val passwordList: List<String> = PasswordGenerator(mode.value).generatePasswords(
                        pwCount = pwCountDropDown.value + 1,
                        pwLength = pwLenInput.value.toInt(),
                        specialChars = additionalCharsInput.value,
                        percentOfSpecialChars = Defaults.getPercentComboValue(percentSpecialChars.value)
                    )

                    // Output
                    passwordList.forEach { singlePassword ->
                        // One password per line
                        resultTextArea.append(
                            """|$singlePassword
                               |
                            """.trimMargin()
                        )
                    }
                }
            }

            val clipboardBtnLabel: String = " Copy to Clipboard "
            button(clipboardBtnLabel) {
                action {
                    memScoped {
                        val cleanedValue: String = resultTextArea.value.trim().replace(Regex("[\n\r]$"), "")

                        text    = "DONE"
                        enabled = false

                        toClipboard(cleanedValue)

                        onTimer(1000) {
                            text    = clipboardBtnLabel
                            enabled = true
                            false
                        }
                    }
                }
            }
        }

        // Read only text area which show the result
        resultTextArea = textarea {
            readonly = true
            stretchy = true
        }

        // A real menu is not really implemented yet in libui, so we use just buttons ;-)
        hbox {
            // Dialog: About
            button(" About ") {
                action {
                    dialogs["about"]?.show()
                }
            }
        }

    }
}

