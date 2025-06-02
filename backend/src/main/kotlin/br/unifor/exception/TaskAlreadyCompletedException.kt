package br.unifor.exception

import br.unifor.extensions.toStringDate
import br.unifor.extensions.toStringTime
import java.time.LocalDateTime

class TaskAlreadyCompletedException(
    hashTask: String, //
    dtDo: LocalDateTime, //
) : APIException(
    title = "Atividade concluída", //
    message = "A atividade $hashTask foi concluída às ${dtDo.toStringTime()} do dia ${dtDo.toStringDate()}.", //
    status = 422, //
)
