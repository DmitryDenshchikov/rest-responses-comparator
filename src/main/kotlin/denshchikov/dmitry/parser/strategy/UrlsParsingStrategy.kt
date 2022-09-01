package denshchikov.dmitry.parser.strategy

class UrlsParsingStrategy : CliArgumentParsingStrategy<List<String>> {

    override fun parse(cliArgumentStringValue: String): List<String> {
        return cliArgumentStringValue.removeSurrounding("[", "]").split(",")
    }

}