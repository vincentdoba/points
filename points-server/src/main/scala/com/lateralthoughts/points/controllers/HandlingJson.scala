package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.model.{RewardingActionInput, JsonFormatter}
import com.lateralthoughts.points.repositories.RewardingActionRepository
import org.json4s.jackson.JsonMethods
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.servlet.RichRequest

/**
 * Trait you need to extend if your endpoints are using JSON format for input/output
 */
trait HandlingJson[T] extends ScalatraServlet with JsonFormatter with JacksonJsonSupport {

  def typeName:String

  before() {
    contentType = formats("json")
  }

  def retrievePostedJsonAnd(f: T => ActionResult)(request: RichRequest)(implicit m: Manifest[T]) = {
    JsonMethods.parseOpt(request.body) match {
      case None => BadRequest("The request body is not a valid JSON object")
      case Some(json) => json.extractOpt[T] match {
        case None => BadRequest(s"The request body is not a JSON object representing $typeName")
        case Some(input) => f(input)
      }
    }
  }


}
