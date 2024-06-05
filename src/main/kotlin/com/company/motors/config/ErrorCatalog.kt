package com.company.motors.config

/**
 * The ErrorCatalog is the place where we defined all error codes and their default messages.
 * Each error code should map to one and only one exception class.
 *
 * e.g.:
 * INTERNAL_SERVER_ERROR -> GenericException
 * ENDPOINT_NOT_FOUND -> EndpointNotFoundException
 * INVALID_UUID -> InvalidUUIDException
 *
 * The name of the enum entry will become the error code. The default message will be used
 * only if the developer does not provide a custom message when instantiating the exception.
 * It's a good practice to use custom messages to be able to provide mor specific information
 * about why that exception was thrown.
 *
 */
enum class ErrorCatalog(val defaultMessage: String) {

    INTERNAL_SERVER_ERROR("Error interno del servidor"),
    RESOURCE_NOT_FOUND("No se encontro recurso solicitado"),
    MISSING_ARGUMENT("No se envio un argumento requerido"),
    INVALID_ARGUMENT("Uno de los parámetros enviados es inválido"),
    REST_CLIENT_ERROR("Ocurrio un error en una llamada rest"),
    TIMEOUT("Se demoro demasiado tratando de realizar la accion solicitada"),
    POKEMON_NOT_FOUND("No se encontro el pokemon"),
    TRAINER_NOT_FOUND("No se encontro el entrenador"),
    POKEDEX_NOT_FOUND("No se encontro el pokedex"),
    POKEMON_TYPE_NOT_FOUND("No se encontro el tipo de pokemon"),
    POKEMON_MOVE_NOT_FOUND("No se encontro el tipo de movimiento"),
    CITY_NOT_FOUND("No se encontro la ciudad espeficada"),
    ABILITY_NOT_FOUND("No se encontro la Habilidad del pokemon"),
    ABILITY_TIMEOUT("El llamado a Ability devolvio error"),
    TYPE_NOT_FOUND("No se encontro el Tipo del pokemon"),
    ACCESS_DENIED("Access is denied"),
    INVALID_COMMAND("Comando invalido"),
    INVALID_TOKEN("Token invalido"),
    ENDPOINT_NOT_FOUND("No se encontro el endpoint solicitado"),
    INVALID_UUID("El id es invalido"),
    INVALID_POKEMON_QUERY_PARAM("Un parametro de la busqueda es invalido"),
    INVALID_TRAINER_QUERY_PARAM("Un parametro de la busqueda de entrenadores es invalido"),
    TRADING_REQUEST_NOT_FOUND("No se encontro el trading request"),
    TRADING_REQUEST_STATUS_NOT_FOUND("No se encontro el status de trading"),
    TRADING_REQUEST_CREATION_ERROR("Error al intentar crear la propuesta de intercambio."),
    TRADING_REQUEST_RESPOND_ERROR("Error al intentar responder a la propuesta de intercambio."),
    INVALID_TRADING_STATUS("El estado no es válido"),
    TRADING_PROPOSAL_STATUS_NOT_FOUND("No se encontro el status de propuesta"),
}
