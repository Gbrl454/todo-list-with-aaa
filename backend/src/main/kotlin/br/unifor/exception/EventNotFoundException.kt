package br.unifor.exception

class EventNotFoundException(idEvent: Long) : APIException(
    title = "Evento não encontrado", //
    message = "Evento $idEvent não encontrado.", //
    status = 404, //
)
