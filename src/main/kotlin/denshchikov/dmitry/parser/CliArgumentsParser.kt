package denshchikov.dmitry.parser

import denshchikov.dmitry.domain.CliArgument
import denshchikov.dmitry.parser.strategy.CliArgumentParsingStrategiesRegistry

class CliArgumentsParser(cliArgumentParsingStrategiesRegistry: CliArgumentParsingStrategiesRegistry) {

    private val argPrefix: String = "--"
    private val keyValSeparator: String = "="
    private val parsingStrategiesRegistry: CliArgumentParsingStrategiesRegistry

    init {
        parsingStrategiesRegistry = cliArgumentParsingStrategiesRegistry
    }

    fun parse(args: Array<String>): Map<CliArgument, Any> {
        val params = mutableMapOf<CliArgument, Any>()

        if (args.isEmpty()) {
            return params
        }

        for (arg in args) {
            val indexOfKeyValSeparator = arg.indexOf(keyValSeparator)
            val argName = CliArgument.getEnumValue(arg.substring(argPrefix.length, indexOfKeyValSeparator))
            val argValue = arg.substring(indexOfKeyValSeparator + 1)

            val parsedValue = parsingStrategiesRegistry.getStrategy(argName).parse(argValue)

            params[argName] = parsedValue!!
        }

        return params
    }

}