package myapp.http4k

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response


fun main() {
    val request = Request(GET, "https://httpbin.org/uuid")

    val client: HttpHandler = JavaHttpClient()

    val response: Response = client(request)

    println(response)
}