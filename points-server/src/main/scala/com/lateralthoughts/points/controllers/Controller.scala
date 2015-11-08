package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.controllers.handlers.HandlingError
import com.lateralthoughts.points.model.ApplicationError
import org.scalatra.{ActionResult, Created, NoContent, Ok}

trait Controller extends HandlingError {

  private def render[T](status: (Any, Map[String, String], String) => ActionResult)(serviceResponse: Either[ApplicationError, T]) = serviceResponse match {
    case Left(error) => buildErrorResponse(error)
    case Right(body) => status(body, Map.empty, "")
  }

  def created = render(Created.apply) _

  def ok = render(Ok.apply) _

  def noContent = render((x, y, z) => NoContent.apply(y, z)) _

}
