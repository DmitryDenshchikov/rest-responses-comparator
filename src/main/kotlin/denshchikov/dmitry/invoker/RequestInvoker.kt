package denshchikov.dmitry.invoker

import org.http4k.client.ApacheClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response

class RequestInvoker {

    private val client: HttpHandler = ApacheClient()

    fun invoke(url: String, requestType: Method): Response = client.invoke(Request(requestType, url))

    fun invoke(url: String, requestType: Method, requestBody: String): Response =
        client.invoke(Request(requestType, url).body(requestBody))

}