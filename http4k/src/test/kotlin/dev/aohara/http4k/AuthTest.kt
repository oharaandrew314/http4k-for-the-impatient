package dev.aohara.http4k

import io.kotest.matchers.shouldBe
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.junit.jupiter.api.Test

class AuthTest {

    @Test
    fun unauthorized() {
        val request = Request(GET, "")
        val response = authenticatedApi(request)

        response.status shouldBe UNAUTHORIZED
    }

    @Test
    fun ok() {
        val withToken = ClientFilters.BearerAuth("letmein")
            .then(authenticatedApi)

        val request = Request(GET, "")
        val response = withToken(request)

        response.status shouldBe OK
        greetingLens(response) shouldBe Greeting("hello user1")
    }
}