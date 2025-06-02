package br.unifor.exception

class DeadlineDateLessCurrentDateException : APIException(
    title = "Data limite menor que a atual", //
    message = "A data limite não pode ser menor que a data atual.", //
    status = 400, //
)
