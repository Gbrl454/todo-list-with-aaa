package br.unifor.entities.events

import br.unifor.entities.GenericEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.*

/**Tabela de domínio de eventos*/
@Entity
@Table(name = "EVENT_DOMAIN", schema = "EVENTS")
class EventDomain(

    /**Identificador único de domínio de evento*/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(
        name = "ID_EVENT_DOMAIN", //
        nullable = false, //
        updatable = false, //
    ) var id: Long, //

    /**Nome do domínio do evento*/
    @Column(
        name = "NM_EVENT_DOMAIN", //
        nullable = false, //
        updatable = false, //
    ) var nmEventDomain: String, //
) : GenericEntity() {
    companion object : PanacheCompanion<Event>

    constructor() : this(
        id = 0, //
        nmEventDomain = "", //
    )

    override fun toString(): String = """
        {
            "id": $id,
            "nmEventDomain": "$nmEventDomain"
        }
        """
}
