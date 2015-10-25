package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.utils.CustomJsonSerializers
import org.json4s.ext.JavaTypesSerializers
import org.json4s.{Formats, DefaultFormats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

/**
 * Trait you need to extend if your endpoints are using JSON format for input/output
 */
trait HandlingJsonEndpoint extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats ++ JavaTypesSerializers.all ++ CustomJsonSerializers.all

  before() {
    contentType = formats("json")
  }


}
