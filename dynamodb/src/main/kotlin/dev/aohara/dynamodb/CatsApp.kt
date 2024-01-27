package dev.aohara.dynamodb

import org.http4k.cloudnative.env.Environment
import org.http4k.connect.amazon.CredentialsChain
import org.http4k.connect.amazon.Environment
import org.http4k.connect.amazon.Profile
import org.http4k.connect.amazon.RegionProvider
import org.http4k.connect.amazon.dynamodb.DynamoDb
import org.http4k.connect.amazon.dynamodb.Http
import org.http4k.connect.amazon.dynamodb.model.TableName
import org.http4k.connect.amazon.instancemetadata.Ec2InstanceProfile
import org.http4k.connect.amazon.sts.StsProfile
import java.util.UUID

fun main() {
    val env = Environment.ENV

    val credentialsProvider = CredentialsChain.Environment(env)
        .orElse(CredentialsChain.StsProfile(env))
        .orElse(CredentialsChain.Ec2InstanceProfile())
        .provider()

    val region = RegionProvider.Environment(env)
        .orElse(RegionProvider.Profile(env))
        .orElse(RegionProvider.Ec2InstanceProfile())
        .orElseThrow()

    val dynamoDb = DynamoDb.Http(region, credentialsProvider)

    val tableName = TableName.of(env["CATS_TABLE_NAME"]!!)

    val cats = Cats(dynamoDb, tableName)

    cats += listOf(kratos, athena, titan)

    println(cats[titan.id]?.name) // Titan
    println(cats[UUID.randomUUID()]?.name) // null

    println(cats[kratos.birthDate].joinToString(", ") {it.name})  // Kratos, Athena

    cats -= kratos.id
    cats -= athena.id
    cats -= titan.id
}