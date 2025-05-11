package br.unifor.entities

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializable
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase

/**
 * Classe base para entidades genéricas que impede a serialização para JSON.
 */
abstract class GenericEntity : PanacheEntityBase, JsonSerializable {
    override fun serialize(
        jsonGenerator: JsonGenerator?, //
        serializerProvider: SerializerProvider?, //
    ) {
        throw UnsupportedOperationException("Classes anotadas com @Entity não devem ser serializadas.")
    }

    override fun serializeWithType(
        jsonGenerator: JsonGenerator?, //
        serializerProvider: SerializerProvider?, //
        typeSerializer: TypeSerializer?, //
    ) {
        throw UnsupportedOperationException("Classes anotadas com @Entity não devem ser serializadas.")
    }
}
