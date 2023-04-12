package myapp.http4k

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.format.Moshi
import java.util.UUID

data class UuidContainer(
    val uuid: UUID
)

val uuidLens = Moshi.autoBody<UuidContainer>().toLens()

fun main() {
    val http: HttpHandler = JavaHttpClient()

    val request = Request(GET, "https://httpbin.org/uuid")

    val response: Response = http(request)

    val container: UuidContainer = uuidLens(response)

    println(container.uuid)
}