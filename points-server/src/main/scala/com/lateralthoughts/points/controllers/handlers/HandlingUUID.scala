package com.lateralthoughts.points.controllers.handlers

import java.util.UUID

import com.lateralthoughts.points.model.{UUIDNotValid, ApplicationError}
import org.scalatra.ActionResult

import scala.util.{Failure, Success, Try}

trait HandlingUUID extends HandlingError {

  def retrieveUUIDFromURLAnd(retrievedId:String)(f: UUID => ActionResult) = Try {
    UUID.fromString(retrievedId)
  } match {
    case Success(id) => f(id)
    case Failure(exception) => buildErrorResponse(ApplicationError(UUIDNotValid, exception.getMessage))
  }

}
