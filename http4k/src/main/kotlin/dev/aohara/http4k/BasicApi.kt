package dev.aohara.http4k

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

typealias HttpHandler = (Request) -> Response

data class Greeting(val message: String)

private val greetingAdapter = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()
    .adapter(Greeting::class.java)

val helloApi: HttpHandler = routes(
    "/hello/{name}" bind GET to { request ->
        val name = request.path("name")
        val greeting = Greeting("hello $name")

        Response(OK)
            .body(greetingAdapter.toJson(greeting))
    }
)

fun main() {
    helloApi
        .asServer(Jetty(8080))
        .start()
        .block()
}





