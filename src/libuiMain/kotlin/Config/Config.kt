package Config

import File.File

/**
 * Configuration loader
 * see "password-generator.ini" for example
 *
 */
class Config(val fileName: String) {
    private val file: File = File(fileName)
    private var config: Map<String, Any?> = mapOf()

    /**
     * This is not a constructor ;)
     */
    init {
        load()
    }

    /**
     * Returns a value of the configuration
     *
     */
    fun get(key: String): String = if ( config.containsKey(key) ) config[key].toString() else Defaults.get(key).toString()

    /**
     * Loads the configuration file and parses it
     *
     */
    private fun load() {
        if ( file.exists() ) {
            val fileContent: String = file.readAllText()

            if ( fileContent.isNotEmpty() ) {
                val parsedConf: Map<String, Any?>? = parse(fileContent)
                if ( parsedConf !== null )
                    config = parsedConf
            }
        }
    }

    /**
     * Parses the content that must look like an ini file configuration
     *
     */
    private fun parse(content: String): Map<String, Any?>? {
        val exprOptions: Set<RegexOption> = setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
        val expr: Regex = Regex("""^([^;\\n][a-zA-Z]+)([ ]?=[ ]?)(".*"|[0-9]{1,})$""", exprOptions)
        val matches: List<MatchResult> = expr.findAll(content).toList()

        return if (matches.isNotEmpty()) {
            val result: MutableMap<String, String?> = mutableMapOf()
            val strExpr: Regex = Regex("""^"(.*)"$""")

            matches.map {
                val value: String = it.groupValues[3].replace(strExpr, "$1").trim()
                val key: String   = it.groupValues[1].trim()

                if ( key == "percentSpecialChars" ) {
                    result[key] = Defaults.getComboValueByPercent(value.toInt()).toString()
                } else result[key] = value

                if ( key == "mode" ) {
                    result[key] = GeneratorMode.valueOf(value.toUpperCase()).modeKey.toString()
                }
            }

            result.toMap()
        } else null
    }
}

