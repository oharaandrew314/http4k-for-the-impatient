package dev.aohara.http4k

import io.kotest.matchers.shouldBe
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ClientFilters
import org.junit.Test

class ContractTest {

    @Test
    fun `say hello`() {
        val withToken = ClientFilters.BearerAuth("opensesame")
            .then(helloContractApi)

        val request = sayHelloContract
            .newRequest()
            .with(nameLens of "Http4k")

        val response = withToken(request)

        response.status shouldBe OK
        greetingLens(response) shouldBe Greeting("hello user2/Http4k")
    }
}
