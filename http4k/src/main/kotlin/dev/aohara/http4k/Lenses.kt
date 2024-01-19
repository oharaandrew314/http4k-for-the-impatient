package dev.aohara.http4k

import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.lens.Path
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes

val nameLens = Path.string().of("name")
val greetingLens = Body.auto<Greeting>().toLens()

val helloRoute = "/hello/$nameLens"

val helloApiWithLenses = routes(
    helloRoute bind GET to { request ->
        val name = nameLens(request)
        val greeting = Greeting("hello $name")

        Response(OK)
            .with(greetingLens of greeting)
    }
)






