package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.model._
import org.scalatra.{BadRequest, InternalServerError, NotImplemented}

/**
  * Converter from Application Error to HTML Error
  */
trait HandlingError {

  def buildErrorResponse(error: ApplicationError) = error.code match {
    case JsonNotValid => BadRequest(error.message)
    case UUIDNotValid => BadRequest(error.message)
    case InputObjectNotValid => BadRequest(error.message)
    case InputObjectIncomplete => BadRequest(error.message)
    case DatabaseError => InternalServerError(error.message)
    case NotCoded => NotImplemented(error.message)
    case _ => InternalServerError(error.message)
  }

}
