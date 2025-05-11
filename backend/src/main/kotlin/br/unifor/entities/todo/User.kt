package br.unifor.entities.todo

import br.unifor.entities.GenericEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.*
import java.time.LocalDateTime

/**Tabela de usuários*/
@Entity
@Table(name = "USER", schema = "TODO")
class User(
    /**Identificador único de usuário*/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "ID_USER", //
        nullable = false, //
        updatable = false, //
    ) var id: Long, //
    /**Nome de acesso*/
    @Column(
        name = "USERNAME", //
        nullable = false, //
        updatable = false, //
    ) var username: String, //

    /**Primeiro nome*/
    @Column(
        name = "FR_NAME", //
        nullable = false, //
        updatable = false, //
    ) var frName: String, //

    /**Nome do meio*/
    @Column(
        name = "MD_NAME", //
        updatable = false, //
    ) var mdName: String?, //

    /**Último nome*/
    @Column(
        name = "LT_NAME", //
        nullable = false, //
        updatable = false, //
    ) var ltName: String, //

    /**E-mail do usuário*/
    @Column(
        name = "USER_EMAIL", //
        nullable = false, //
        updatable = false, //
    ) var email: String, //

    /**Data de inclusão do usuário*/
    @Column(
        name = "DT_INCLUSION", //
        nullable = false, //
        updatable = false, //
    ) var dtInclusion: LocalDateTime, //

    /**Indica se o usuário está ativo*/
    @Column(
        name = "IS_ACTIVE", //
        nullable = false, //
        updatable = false, //
    ) var isActive: Boolean, //
) : GenericEntity() {
    companion object : PanacheCompanion<User>

    constructor() : this(
        id = 0, //
        username = "", //
        frName = "", //
        mdName = null, //
        ltName = "", //
        email = "", //
        dtInclusion = LocalDateTime.now(), //
        isActive = true, //
    )
}