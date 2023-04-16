package dev.aohara.http4k

import org.http4k.core.Filter
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ResponseFilters

fun interface Filter : (HttpHandler) -> HttpHandler

private val printRequestAndResponse = Filter { next: HttpHandler ->
    { request ->
        println(request)
        val response = next(request)
        println(response)
        response
    }
}

private val setFooHeader = ResponseFilters.SetHeader("Foo", "Bar")

val filteredApi: HttpHandler = printRequestAndResponse
    .then(setFooHeader)
    .then { Response(OK) }
