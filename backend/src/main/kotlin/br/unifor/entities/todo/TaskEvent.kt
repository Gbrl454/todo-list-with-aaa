package br.unifor.entities.todo

import br.unifor.entities.GenericEntity
import br.unifor.entities.events.Event
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.*
import java.time.LocalDateTime

/**Tabela de eventos da atividade*/
@Entity
@Table(name = "TASK_EVENT", schema = "TODO")
class TaskEvent(
    /**Identificador único de evento*/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "ID_TASK_EVENT", //
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

    /**Atividade do evento*/
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "ID_TASK", //
        referencedColumnName = "ID_TASK", //
        nullable = false, //
        updatable = false, //
    ) var task: Task, //

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
    companion object : PanacheCompanion<TaskEvent> {
        private fun makeEvent(
            task: Task, //
            userOperator: User, //
            idEvent: Long, //
        ): TaskEvent = TaskEvent().apply {
            this.event = Event.findEventByIdOrException(idEventDomain = 200, idEventFaixa = idEvent)
            this.task = task
            this.userOperator = userOperator
        }

        /**Atividade criada*/
        fun create(
            task: Task, //
            userOperator: User, //
        ): TaskEvent = makeEvent(
            task = task, //
            userOperator = userOperator, //
            idEvent = 1, //
        )

        /**Atividade desativada*/
        fun disable(
            task: Task, //
            userOperator: User, //
        ): TaskEvent = makeEvent(
            task = task, //
            userOperator = userOperator, //
            idEvent = 2, //
        )

        /**Atividade realizada*/
        fun complete(
            task: Task, //
            userOperator: User, //
        ): TaskEvent = makeEvent(
            task = task, //
            userOperator = userOperator, //
            idEvent = 3, //
        )

        /**Atividade tornada pública*/
        fun turnPublic(
            task: Task, //
            userOperator: User, //
        ): TaskEvent = makeEvent(
            task = task, //
            userOperator = userOperator, //
            idEvent = 4, //
        )

        /**Atividade tornada privada*/
        fun turnPrivate(
            task: Task, //
            userOperator: User, //
        ): TaskEvent = makeEvent(
            task = task, //
            userOperator = userOperator, //
            idEvent = 5, //
        )
    }

    constructor() : this(
        id = 0, //
        event = Event(), //
        task = Task(), //
        userOperator = User(), //
        dtOccurrence = LocalDateTime.now(), //
    )

    override fun toString(): String = """
        {
            "id": $id,
            "event": $event,
            "task": $task,
            "userOperator": $userOperator,
            "dtOccurrence": "$dtOccurrence"
        }
        """
}