package com.lateralthoughts.points.controllers.handlers

import com.lateralthoughts.points.model.{ApplicationError, InputObjectNotValid, JsonFormatter, JsonNotValid}
import org.json4s.jackson.JsonMethods
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.servlet.RichRequest

/**
  * Trait you need to extend if your endpoints are using JSON format for input/output
  */
trait HandlingJson extends ScalatraServlet with HandlingError with JsonFormatter with JacksonJsonSupport {

  before() {
    contentType = formats("json")
  }

  def retrievePostedJsonAnd[T](f: T => ActionResult, objectName: String)(request: RichRequest)(implicit manifest: Manifest[T]) = {
    JsonMethods.parseOpt(request.body) match {
      case None => buildErrorResponse(ApplicationError(JsonNotValid, "The request body is not a valid JSON object"))
      case Some(json) => json.extractOpt[T] match {
        case None => buildErrorResponse(ApplicationError(InputObjectNotValid, s"The request body is not a JSON object representing $objectName"))
        case Some(input) => f(input)
      }
    }
  }


}
