package br.unifor.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import java.math.BigDecimal

typealias AnyJson = @Serializable(with = AnySerializer::class) Any

object AnySerializer : KSerializer<Any> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Any", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Any) {
        when (encoder) {
            is JsonEncoder -> {
                encoder.encodeJsonElement(
                    when (value) {
                        is Int -> JsonPrimitive(value)
                        is Double -> JsonPrimitive(value)
                        is Long -> JsonPrimitive(value)
                        is BigDecimal -> JsonPrimitive(value)
                        is String -> JsonPrimitive(value)
                        is Boolean -> JsonPrimitive(value)

                        else -> JsonPrimitive(value.toString())
                    }
                )
            }

            else -> encoder.encodeString(value.toString())
        }
    }

    override fun deserialize(decoder: Decoder): Any {
        TODO()
    }
}
