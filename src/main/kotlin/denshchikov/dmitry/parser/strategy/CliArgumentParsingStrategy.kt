package denshchikov.dmitry.parser.strategy

interface CliArgumentParsingStrategy<out T> {

    fun parse(cliArgumentStringValue: String): T

}