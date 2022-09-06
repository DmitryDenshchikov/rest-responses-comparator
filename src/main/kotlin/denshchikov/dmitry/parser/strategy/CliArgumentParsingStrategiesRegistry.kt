package denshchikov.dmitry.parser.strategy

import denshchikov.dmitry.domain.CliArgument

class CliArgumentParsingStrategiesRegistry {

    private val strategies: Map<CliArgument, CliArgumentParsingStrategy<*>> = mapOf(
        CliArgument.URLS to UrlsParsingStrategy(),
        CliArgument.REQUEST_BODY to RequestBodyParsingStrategy(),
        CliArgument.REQUEST_TYPE to RequestTypeParsingStrategy(),
        CliArgument.FIELDS_TO_EXCLUDE to IgnoredFieldsParsingStrategy()
    )

    fun getStrategy(arg: CliArgument) : CliArgumentParsingStrategy<*> = strategies.getValue(arg)

}