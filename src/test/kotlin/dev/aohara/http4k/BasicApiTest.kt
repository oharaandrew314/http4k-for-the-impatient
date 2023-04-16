package dev.aohara.http4k

import io.kotest.matchers.shouldBe
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.junit.Test

class BasicApiTest {

    @Test
    fun `say hello - in memory`() {
        val request = Request(GET, "/hello/Http4k")

        val response = helloApi(request)
        response.status shouldBe OK
        response.bodyString() shouldBe """{"message":"hello Http4k"}"""
    }

    @Test
    fun `say hello - port based`() {
        val server = helloApi.asServer(SunHttp(0))
        val client = JavaHttpClient()

        server.start().use {
            val request = Request(GET, "http://localhost:${server.port()}/hello/Http4k")

            val response = client(request)
            response.status shouldBe OK
            response.bodyString() shouldBe """{"message":"hello Http4k"}"""
        }
    }
}
