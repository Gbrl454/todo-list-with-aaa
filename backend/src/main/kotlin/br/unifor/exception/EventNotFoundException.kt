package br.unifor.exception

class EventNotFoundException(idEventFaixa: Long) : APIException(
    title = "Evento não encontrado", //
    message = "Evento $idEventFaixa não encontrado.", //
    status = 404, //
)
