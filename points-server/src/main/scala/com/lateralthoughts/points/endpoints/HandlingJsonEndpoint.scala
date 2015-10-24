package com.lateralthoughts.points.endpoints

import org.json4s.{Formats, DefaultFormats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

trait HandlingJsonEndpoint extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }


}
