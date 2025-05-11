package br.unifor.exception.model

import jakarta.validation.ConstraintViolation
import kotlinx.serialization.Serializable

@Serializable
data class FieldError(val field: String, val message: String) {
    constructor(constraintViolation: ConstraintViolation<*>) : this(
        field = constraintViolation.propertyPath.toString().split(".").last(), //
        message = constraintViolation.message, //
    )

    constructor(missingField: String) : this(
        field = missingField, //
        message = "O campo é obrigatório.", //
    )
}