package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.model.JsonFormatter
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

/**
 * Trait you need to extend if your endpoints are using JSON format for input/output
 */
trait HandlingJson extends ScalatraServlet with JsonFormatter with JacksonJsonSupport {

  before() {
    contentType = formats("json")
  }


}
