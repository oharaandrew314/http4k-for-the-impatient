package dev.aohara.http4k

import io.kotest.matchers.shouldBe
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.junit.Test

class FiltersTest {

    @Test
    fun `test filter`() {
        val request = Request(GET, "")
        val response = filteredApi(request)

        response.status shouldBe OK
        response.header("Foo") shouldBe "Bar"
    }
}
