package dev.aohara.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class GreetingDtoV1(
    val message: String
)

@SpringBootApplication(scanBasePackages = ["dev/aohara/spring"])
@RestController
class HelloController {

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable("name") name: String): ResponseEntity<GreetingDtoV1> {
        val greeting = GreetingDtoV1("hello $name")
        return ResponseEntity.ok(greeting)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(HelloController::class.java, *args)
}
