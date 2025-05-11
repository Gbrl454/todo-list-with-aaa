package br.unifor.entities.todo

import br.unifor.entities.GenericEntity
import br.unifor.entities.events.Event
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.*
import java.time.LocalDateTime

/**Tabela de eventos do usuário*/
@Entity
@Table(name = "USER_EVENT", schema = "TODO")
class UserEvent(
    /**Identificador único de evento*/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "ID_USER_EVENT", //
        nullable = false, //
        updatable = false, //
    ) var id: Long, //

    /**Evento ocorrido*/
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "ID_EVENT", //
        referencedColumnName = "ID_EVENT", //
        nullable = false, //
        updatable = false, //
    ) var event: Event, //

    /**Usuário do evento*/
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "ID_USER", //
        referencedColumnName = "ID_USER", //
        nullable = false, //
        updatable = false, //
    ) var user: User, //

    /**Usuário que realizou o evento*/
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "ID_USER_OPERATOR", //
        referencedColumnName = "ID_USER", //
        nullable = false, //
        updatable = false, //
    ) var userOperator: User, //

    /**Data de acontecimento do evento*/
    @Column(
        name = "DT_OCCURRENCE", //
        nullable = false, //
        updatable = false, //
    ) var dtOccurrence: LocalDateTime, //
) : GenericEntity() {
    companion object : PanacheCompanion<UserEvent> {
        /**Usuário criado*/
        fun create(user: User, userOperator: User): UserEvent = UserEvent().apply {
            this.event = Event.findEventByIdOrException(idEvent = 1)
            this.user = user
            this.userOperator = userOperator
        }

        /**Usuário desativado*/
        fun disable(user: User, userOperator: User): UserEvent = UserEvent().apply {
            this.event = Event.findEventByIdOrException(idEvent = 2)
            this.user = user
            this.userOperator = userOperator
        }

    }

    constructor() : this(
        id = 0, //
        event = Event(), //
        user = User(), //
        userOperator = User(), //
        dtOccurrence = LocalDateTime.now(), //
    )
}