package dev.aohara.dynamodb

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.http4k.format.ListAdapter
import org.http4k.format.MapAdapter
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
private object CatsJsonAdapterFactory : JsonAdapter.Factory by KotshiCatsJsonAdapterFactory

val catsMoshi = Moshi.Builder()
    .add(CatsJsonAdapterFactory)
    .add(ListAdapter)
    .add(MapAdapter)
    .asConfigurable()
    .withStandardMappings()
    .done()