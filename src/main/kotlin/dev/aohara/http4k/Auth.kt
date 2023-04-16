package dev.aohara.http4k

import org.http4k.core.Request
import org.http4k.core.RequestContexts
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.lens.RequestContextKey

val authLookup = { token: String ->
    when(token) {
        "letmein" -> "user1"
        "opensesame" -> "user2"
        else -> null
    }
}

val requestContexts = RequestContexts()
val userIdLens = RequestContextKey.required<String>(requestContexts)

private val helloUser = { request: Request ->
    val userId = userIdLens(request)

    Response(OK)
        .with(greetingLens of Greeting("hello $userId"))
}

val authenticatedApi = ServerFilters
    .InitialiseRequestContext(requestContexts)
    .then(ServerFilters.BearerAuth(userIdLens, authLookup))
    .then(helloUser)
