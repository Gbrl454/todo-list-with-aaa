package br.unifor.dto

import br.unifor.core.entities.ca.Estabelecimento
import br.unifor.core.entities.ca.cp.Pessoa
import br.unifor.core.util.PessoaUtil
import br.unifor.exception.APIException
import br.unifor.exception.PessoaLogadaNotFoundException
import kotlinx.serialization.Serializable
import org.eclipse.microprofile.jwt.JsonWebToken
import kotlin.jvm.optionals.getOrNull

val estabelecimentos: List<Pair<Int, String>> by lazy {
    Estabelecimento.list<Estabelecimento>("flAtivo = 'S'") //
        .map { it.cdEstabelecimento.toInt() to it.dsEstabelecimento }
}

@Serializable
data class Usuario(
    val estabelecimento: Int, //
    val matricula: Long, //
    val nomeCompleto: String, //
    val nome: String, //
    val sobrenome: String, //
    val email: String, //
    val departamento: String, //
    val isAluno: Boolean, //
    val isProfessor: Boolean, //
)

fun Usuario.getPessoaLogada(): Pessoa =
    PessoaUtil.findByMatricula("${this.estabelecimento}${this.matricula}") ?: throw PessoaLogadaNotFoundException()

fun String.toUsuario(
    estabelecimento: String? = null, //
    matricula: String? = null, //
    nome: String? = "", //
    sobrenome: String? = "", //
    email: String? = "", //
    departamento: String? = "", //
): Usuario {
    val estab = estabelecimentos.firstOrNull {
        (if (this.length < 8) //
            this.padStart(8, '0') //
        else this).startsWith("${it.first}")
    }?.first ?: 0
    return Usuario(
        estabelecimento = estabelecimento?.toInt() ?: estab, //
        matricula = matricula?.toLong() ?: (if (estab != 0) this.removePrefix(estab.toString()) else this).toLong(), //
        nomeCompleto = "$nome $sobrenome", //
        nome = nome ?: "", //
        sobrenome = sobrenome ?: "", //
        email = email ?: "", //
        departamento = departamento ?: "", //
        isAluno = estab == 0, //
        isProfessor = estab == 731, //
    )
}

fun JsonWebToken.toUsuario() = this.name?.toUsuario(
    estabelecimento = this.claim<String>("estabelecimento").getOrNull(), //
    matricula = this.claim<String>("matricula").getOrNull(), //
    nome = this.claim<String>("given_name").getOrNull(), //
    sobrenome = this.claim<String>("family_name").getOrNull(), //
    email = this.claim<String>("email").getOrNull(), //
    departamento = this.claim<String>("departamento").getOrNull(), //
) ?: throw APIException(
    title = "Falha ao tentar realizar autenticação!", //
    message = "O token de acesso do usuário não foi encontrado, tente novamente.", //
    status = 401, //
)
