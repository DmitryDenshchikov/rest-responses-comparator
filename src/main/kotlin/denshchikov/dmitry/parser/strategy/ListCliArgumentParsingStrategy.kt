package denshchikov.dmitry.parser.strategy

interface ListCliArgumentParsingStrategy : CliArgumentParsingStrategy<List<String>> {

    override fun parse(cliArgumentStringValue: String): List<String> {
        return cliArgumentStringValue.removeSurrounding("[", "]").split(",")
    }

}