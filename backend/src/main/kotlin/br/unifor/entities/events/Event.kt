package br.unifor.entities.events

import br.unifor.entities.GenericEntity
import br.unifor.exception.EventNotFoundException
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.*

/**Tabela de eventos*/
@Entity
@Table(name = "EVENT", schema = "EVENTS")
class Event(

    /**Identificador único do evento*/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "ID_EVENT", //
        nullable = false, //
        updatable = false, //
    ) var id: Long, //

    /**Domínio do evento*/
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "ID_EVENT_DOMAIN", //
        referencedColumnName = "ID_EVENT_DOMAIN", //
        nullable = false, //
        updatable = false, //
    ) var eventDomain: EventDomain, //

    /**Nome do evento*/
    @Column(
        name = "NM_EVENT", //
        nullable = false, //
        updatable = false, //
    ) var nmEvent: String, //
) : GenericEntity() {
    companion object : PanacheCompanion<Event> {
        fun findEventByIdOrException(idEvent: Long): Event = Event.findById(idEvent) //
            ?: throw EventNotFoundException(idEvent = idEvent)
    }

    constructor() : this(
        id = 0, //
        eventDomain = EventDomain(), //
        nmEvent = "", //
    )
}
