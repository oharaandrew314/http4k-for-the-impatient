package dev.aohara.dynamodb

import org.http4k.connect.amazon.dynamodb.DynamoDb
import org.http4k.connect.amazon.dynamodb.mapper.DynamoDbTableMapperSchema
import org.http4k.connect.amazon.dynamodb.mapper.tableMapper
import org.http4k.connect.amazon.dynamodb.model.Attribute
import org.http4k.connect.amazon.dynamodb.model.AttributeValue
import org.http4k.connect.amazon.dynamodb.model.IndexName
import org.http4k.connect.amazon.dynamodb.model.TableName
import org.http4k.format.ConfigurableMoshi
import java.time.LocalDate
import java.util.UUID

private val searchIndex = DynamoDbTableMapperSchema.GlobalSecondary<LocalDate, UUID>(
    indexName = IndexName.of("search"),
    hashKeyAttribute = Attribute.localDate().required("birthDate"),
    sortKeyAttribute = Attribute.uuid().required("id")
)

class Cats(dynamoDb: DynamoDb, tableName: TableName) {

    private val table = dynamoDb.tableMapper<Cat, UUID, Unit>(
        tableName = tableName,
        hashKeyAttribute = Attribute.uuid().required("id"),
        autoMarshalling = ConfigurableMoshi(catsMoshi)
    )

    // simple
    operator fun get(id: UUID) = table[id, Unit]
    operator fun plusAssign(cat: Cat) = table.save(cat)
    operator fun minusAssign(id: UUID) = table.delete(id)

    // batch
    operator fun get(ids: Collection<UUID>) = table.batchGet(ids.map { it to Unit })
    operator fun plusAssign(cats: List<Cat>) = table.batchSave(cats)

    // index operations
    operator fun get(birthDate: LocalDate): Sequence<Cat> = table.index(searchIndex).query(birthDate)

    fun search(birthDate: LocalDate, name: String): Sequence<Cat> = table.index(searchIndex).query(
        KeyConditionExpression = "birthDate = :birthDate",
        FilterExpression = "name = :name",
        ExpressionAttributeValues = mapOf(
            ":birthDate" to AttributeValue.Str(birthDate.toString()),
            ":name" to AttributeValue.Str(name)
        )
    )

    fun createTable() = table.createTable(searchIndex)
}