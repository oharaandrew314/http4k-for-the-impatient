package myapp.http4k

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

typealias HttpHandler = (Request) -> Response

val sayHello: HttpHandler = { request: Request ->
    val name = request.path("name")
    Response(OK).body("hello $name")
}

val helloApi: HttpHandler = routes(
    "/hello/{name}" bind GET to sayHello
)

fun main() = helloApi
    .asServer(Undertow(8080))
    .start()
    .block()
