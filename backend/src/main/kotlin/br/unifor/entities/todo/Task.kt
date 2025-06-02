package br.unifor.entities.todo

import br.unifor.entities.GenericEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.*
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*

/**Tabela de atividades*/
@Entity
@Table(name = "TASK", schema = "TODO")
class Task(
    /**Identificador único de atividade*/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "ID_TASK", //
        nullable = false, //
        updatable = false, //
    ) var id: Long, //

    /**Hash da atividade*/
    @Column(
        name = "HASH_TASK", //
        nullable = false, //
        updatable = false, //
    ) var hashTask: String, //

    /**Usuário dono da atividade*/
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "ID_USER_OWNER", //
        referencedColumnName = "ID_USER", //
        nullable = false, //
        updatable = false, //
    ) var userOwner: User, //

    /**Nome da atividade*/
    @Column(
        name = "NM_TASK", //
        nullable = false, //
    ) var nmTask: String, //

    /**Descrição da atividade*/
    @Column(
        name = "DS_TASK", //
        nullable = false, //
    ) var dsTask: String, //

    /**Indica se a atividade é privada*/
    @Column(
        name = "IS_PRIVATE_TASK", //
        nullable = false, //
    ) var isPrivateTask: Boolean, //

    /**Data limite da atividade*/
    @Column(
        name = "DT_DEADLINE", //
        nullable = false, //
        updatable = false, //
    ) var dtDeadline: LocalDateTime, //

    /**Data de realização da atividade*/
    @Column(
        name = "DT_DO", //
    ) var dtDo: LocalDateTime?, //

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
    ) var isActive: Boolean, //
) : GenericEntity() {
    companion object : PanacheCompanion<Task> {
        fun findByHash(hashTask: String): Task? = Task.find("hashTask = '$hashTask'").firstResult()
    }

    constructor() : this(
        id = 0, //
        hashTask = "", //
        userOwner = User(), //
        nmTask = "", //
        dsTask = "", //
        isPrivateTask = true, //
        dtDeadline = LocalDateTime.MAX, //
        dtDo = null, //
        dtInclusion = LocalDateTime.now(), //
        isActive = true, //
    )

    fun generateHashAndPersist(
        userOwner: User, //
    ): Task = apply {
        this.userOwner = userOwner
        this.hashTask = Base64.getUrlEncoder() //
            .withoutPadding() //
            .encodeToString(
                MessageDigest.getInstance("SHA-256") //
                    .digest("${System.currentTimeMillis()}${this.nmTask}${userOwner.username}${this.dsTask}".toByteArray())
            ).take(99)
        this.persist()
    }
}