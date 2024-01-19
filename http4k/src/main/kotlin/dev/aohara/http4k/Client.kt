package dev.aohara.http4k

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request


fun main() {
    val internet: HttpHandler = JavaHttpClient()

    val request = Request(GET, "https://httpbin.org/uuid")
    val response = internet(request)

    println(response.bodyString())
}