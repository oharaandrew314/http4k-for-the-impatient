package dev.aohara.dynamo

import dev.aohara.dynamodb.Cats
import dev.aohara.dynamodb.athena
import dev.aohara.dynamodb.kratos
import dev.aohara.dynamodb.titan
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.sequences.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.http4k.connect.amazon.dynamodb.FakeDynamoDb
import org.http4k.connect.amazon.dynamodb.model.TableName
import org.junit.jupiter.api.Test
import java.util.UUID

class CatsTest {
    private val cats = Cats(
        dynamoDb = FakeDynamoDb().client(),
        tableName = TableName.of("cats")
    )

    init {
        cats.createTable()
    }

    @Test
    fun `get missing`() {
        cats[UUID.randomUUID()].shouldBeNull()
    }

    @Test
    fun `save and get`() {
        cats += kratos

        cats[kratos.id] shouldBe kratos
    }

    @Test
    fun `batch save`() {
        cats += listOf(kratos, athena, titan)

        cats[kratos.id] shouldBe kratos
        cats[athena.id] shouldBe athena
        cats[titan.id] shouldBe titan
    }

    @Test
    fun `get by birth date`() {
        cats += listOf(kratos, athena, titan)

        cats[kratos.birthDate].toList().shouldContainExactlyInAnyOrder(kratos, athena)
    }

    @Test
    fun `delete missing`() {
        cats -= UUID.randomUUID()  // no error
    }

    @Test
    fun `delete cat`() {
        cats += kratos
        cats -= kratos.id

        cats[kratos.id].shouldBeNull()
    }

    @Test
    fun `search cat`() {
        cats += listOf(kratos, athena, titan)

        cats.search(kratos.birthDate, athena.name).shouldContainExactly(athena)
    }
}