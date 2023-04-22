package dev.aohara.http4k

import org.http4k.contract.contract
import org.http4k.contract.div
import org.http4k.contract.meta
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.contract.security.BearerAuthSecurity
import org.http4k.contract.ui.swaggerUiLite
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

val sayHelloSpec = "/hello" / nameLens meta {
    operationId = "sayHello"
    summary = "Say Hello"

    returning(OK, greetingLens to Greeting("hello sample"))
} bindContract GET

private val sayHello = sayHelloSpec to { name ->
    { request ->
        val userId = userIdLens(request)
        val greeting = Greeting("hello $userId/$name")
        Response(OK)
            .with(greetingLens of greeting)
    }
}

private val api = contract {
    routes += sayHello
    security = BearerAuthSecurity(userIdLens, authLookup)

    renderer = OpenApi3(
        apiInfo = ApiInfo("Hello API", "1.0.0")
    )
    descriptionPath = "openapi.json"
}

private val ui = swaggerUiLite {
    url = "openapi.json"
}

val helloContractApi = ServerFilters
    .InitialiseRequestContext(requestContexts)
    .then(routes(api, ui))

fun main() {
    helloContractApi
        .asServer(Undertow(8080))
        .start()
        .block()
}