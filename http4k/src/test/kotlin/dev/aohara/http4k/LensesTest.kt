package dev.aohara.http4k

import io.kotest.matchers.shouldBe
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.junit.jupiter.api.Test

class LensesTest {

    @Test
    fun `say hello`() {
        val request = Request(GET, helloRoute)
            .with(nameLens of "Http4k")

        val response = helloApiWithLenses(request)

        response.status shouldBe OK
        greetingLens(response) shouldBe Greeting("hello Http4k")
    }
}
