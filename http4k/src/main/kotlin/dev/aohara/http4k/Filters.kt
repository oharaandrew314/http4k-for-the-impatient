package dev.aohara.http4k

import org.http4k.core.Filter
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.then
import org.http4k.filter.ResponseFilters

private val printTransaction = Filter { next: HttpHandler ->
    { request ->
        println("Request:\n$request")
        val response = next(request)
        println("Response:\n$response")
        response
    }
}

private val setFooHeader = ResponseFilters
    .SetHeader("Foo", "Bar")

val filteredApi: HttpHandler = printTransaction
    .then(setFooHeader)
    .then(helloApi)

fun main() {
    val request = Request(Method.GET, "/hello/Http4k")
    val response = filteredApi(request)
}








