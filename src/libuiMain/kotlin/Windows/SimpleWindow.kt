package windows

import libui.ktx.*

interface SimpleWindowInterface {
    fun show()
    fun hide()
    fun dispose()
}

class SimpleWindow(
    private val title: String,
    private val width: Int,
    private val height: Int,
    val init: Window.() -> Unit = {}
) : SimpleWindowInterface {
    private var windowInstance: Set<Window> = emptySet()

    private fun createWindow(): Set<Window> =
        setOf(Window(
            title,
            width,
            height
        ).apply {
            onClose { this@SimpleWindow.hide(); true }
            onShouldQuit { this@SimpleWindow.dispose(); true }

            fullscreen = false

            init()
        })

    private fun applyToWindow(block: Window.() -> Unit) =
        windowInstance.first().apply(block)

    override fun show() {
        if ( windowInstance.isEmpty() ) {
            windowInstance = createWindow()
            applyToWindow { show() }
        }
    }

    override fun hide() {
        if ( windowInstance.isNotEmpty() ) {
            applyToWindow { hide() }
            windowInstance = emptySet()
        }
    }

    override fun dispose() {
        if ( windowInstance.isNotEmpty() ) {
            applyToWindow { dispose() }
        }
    }
}
