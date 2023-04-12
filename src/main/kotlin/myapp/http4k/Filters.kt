package myapp.http4k

import org.http4k.core.Filter
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.then
import org.http4k.filter.ResponseFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun interface Filter : (HttpHandler) -> HttpHandler

private val printResponse = Filter { next: HttpHandler ->
    { request: Request ->
        val response = next(request)
        println(response)
        response
    }
}

private val setFooHeader = ResponseFilters.SetHeader("Foo", "Bar")

private val instrumented: HttpHandler = printResponse
    .then(setFooHeader)
    .then(helloApi)

fun main() = instrumented
    .asServer(Undertow(8080))
    .start()
    .block()