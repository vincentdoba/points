package com.lateralthoughts.points

import org.scalatra._
import scalate.ScalateSupport

class PointsServlet extends PointsStack {

  get("/") {
    "coucou"
  }



}
