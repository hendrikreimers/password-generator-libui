package windows.control

import kotlinx.cinterop.cstr
import platform.posix.memcpy
import platform.windows.*

/**
 * Copies a string to the system clipboard
 *
 */
fun toClipboard(lastLine:String?){
    val len = lastLine!!.length + 1
    val hMem = GlobalAlloc(GMEM_MOVEABLE, len.toULong())

    memcpy(GlobalLock(hMem), lastLine.cstr, len.toULong())
    GlobalUnlock(hMem)

    val hwnd = HWND_TOP

    OpenClipboard(hwnd)
    EmptyClipboard()
    SetClipboardData(CF_TEXT, hMem)
    CloseClipboard()
}