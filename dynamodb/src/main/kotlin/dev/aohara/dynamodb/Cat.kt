package dev.aohara.dynamodb

import se.ansman.kotshi.JsonSerializable
import java.time.LocalDate
import java.util.UUID

@JsonSerializable
data class Cat(
    val name: String,
    val birthDate: LocalDate,
    val id: UUID = UUID.randomUUID()
)

val kratos = Cat(name = "Kratos", birthDate = LocalDate.of(2023, 9, 4))
val athena = Cat(name = "Athena", birthDate = LocalDate.of(2023, 9, 4))
val titan = Cat(name = "Titan", birthDate = LocalDate.of(2013, 6, 10))