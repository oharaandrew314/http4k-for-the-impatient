package myapp.http4k

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val realServer = helloApi
        .asServer(SunHttp(0))
        .start()

    val realClient = JavaHttpClient()

    Request(GET, "http://localhost:${realServer.port()}/hello/real")
        .let(realClient)
        .also(::println)

    Request(GET, "/hello/test")
        .let(helloApi)
        .also(::println)
}