package File

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.*

/**
 * Class that helps to access and read files
 *
 */
data class File(private val filePath: String) {

    /**
     * Checks if file exists
     *
     */
    fun exists(): Boolean {
        return access(filePath, F_OK) != -1
    }

    /**
     * Reads the file content as string and returns result
     *
     */
    fun readAllText(): String {
        val returnBuffer = StringBuilder()
        val file = fopen(filePath, "r") ?: throw IllegalArgumentException("Cannot open input file $filePath")

        try {
            memScoped {
                val readBufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(readBufferLength)
                var line = fgets(buffer, readBufferLength, file)?.toKString()
                while (line != null) {
                    returnBuffer.append(line)
                    line = fgets(buffer, readBufferLength, file)?.toKString()
                }
            }
        } finally {
            fclose(file)
        }

        return returnBuffer.toString()
    }

}