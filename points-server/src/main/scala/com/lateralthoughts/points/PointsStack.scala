package com.lateralthoughts.points

import org.scalatra._
import scalate.ScalateSupport
import org.fusesource.scalate.{ TemplateEngine, Binding }
import org.fusesource.scalate.layout.DefaultLayoutStrategy
import javax.servlet.http.HttpServletRequest
import collection.mutable

trait PointsStack extends ScalatraServlet with ScalateSupport {

  notFound {
    "404 Not Found"
  }

}
