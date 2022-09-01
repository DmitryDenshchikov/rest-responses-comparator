package denshchikov.dmitry

import denshchikov.dmitry.domain.CliArgument.*
import denshchikov.dmitry.invoker.RequestInvoker
import denshchikov.dmitry.parser.CliArgumentsParser
import denshchikov.dmitry.parser.strategy.CliArgumentParsingStrategiesRegistry

import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response


fun main(args: Array<String>) {
    val argsMap = CliArgumentsParser(CliArgumentParsingStrategiesRegistry()).parse(args)
    val requestType = Method.valueOf(argsMap.getValue(REQUEST_TYPE) as String)
    val requestInvoker = RequestInvoker()
    val responses = mutableListOf<Response>()

    @Suppress("UNCHECKED_CAST")
    for (url in argsMap.getValue(URLS) as List<String>) {
        responses += when (requestType) {
            GET -> requestInvoker.invoke(url, requestType)
            POST -> requestInvoker.invoke(url, requestType, argsMap.getValue(REQUEST_BODY) as String)
            else -> throw UnsupportedOperationException("Only GET and POST methods are supported now")
        }
    }

    for (i in 0 until responses.size) {
        for (j in i + 1 until responses.size) {
            println(responses[i].body == responses[j].body)
        }
    }

}
