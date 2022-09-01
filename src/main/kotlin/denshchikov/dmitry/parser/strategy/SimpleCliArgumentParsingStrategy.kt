package denshchikov.dmitry.parser.strategy

interface SimpleCliArgumentParsingStrategy : CliArgumentParsingStrategy<String> {

    override fun parse(cliArgumentStringValue: String): String = cliArgumentStringValue

}