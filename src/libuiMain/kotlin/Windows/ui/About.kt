package windows.ui

import libui.ktx.label
import libui.ktx.vbox
import windows.SimpleWindow

fun aboutWindow(): SimpleWindow = SimpleWindow(
    "About",
    150,
    50
) {
    margined = true

    vbox {
        label("Made by")
        label("Hendrik Reimers")
        label("www.core23.com")
    }
}