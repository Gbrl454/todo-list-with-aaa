package br.unifor.serialization

import br.unifor.core.entities.ca.Banco
import br.unifor.core.entities.ca.cp.Pessoa
import br.unifor.core.entities.ca.ctr.BancoAgenciaCarteira
import br.unifor.core.entities.ca.fat.Financeiro
import br.unifor.core.entities.ca.fat.Modalidade
import br.unifor.core.entities.ca.fat.ValorIndice
import br.unifor.core.entities.ca.pl.DominioFaixa
import br.unifor.core.enums.Dominios
import br.unifor.dto.BancoDTO
import br.unifor.dto.ModalidadeDTO
import br.unifor.dto.PessoaDTO
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
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
