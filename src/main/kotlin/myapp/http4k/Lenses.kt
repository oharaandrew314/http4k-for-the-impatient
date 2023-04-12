package myapp.http4k

import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.lens.Path
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

data class Greeting(val message: String)

val nameLens = Path.string().of("name")

val greetingLens = Body.auto<Greeting>().toLens()

val greetingApi = "/hello/$nameLens" bind GET to { req ->
    val name = nameLens(req)
    val greeting = Greeting("hello $name")

    Response(Status.OK).with(greetingLens of greeting)
}

fun main() = routes(greetingApi)
    .asServer(Undertow(8080))
    .start()
    .block()
