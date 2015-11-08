package com.lateralthoughts.points.controllers.handlers

import com.lateralthoughts.points.model._
import org.scalatra.{BadRequest, InternalServerError, NotFound, NotImplemented}

/**
  * Converter from Application Error to HTML Error
  */
trait HandlingError {

  def buildErrorResponse(error: ApplicationError) = error.code match {
    case JsonNotValid => BadRequest(error)
    case UUIDNotValid => BadRequest(error)
    case InputObjectNotValid => BadRequest(error)
    case InputObjectIncomplete => BadRequest(error)
    case RecordNotFound => NotFound(error)
    case DatabaseError => InternalServerError(error)
    case NotCoded => NotImplemented(error)
    case _ => InternalServerError(error)
  }

}
