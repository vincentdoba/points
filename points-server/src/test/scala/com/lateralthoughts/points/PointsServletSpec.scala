package com.lateralthoughts.points

import org.scalatra.test.specs2._

class PointsServletSpec extends ScalatraSpec { def is =
  "GET / on PointsServlet"                     ^
    "should return status 200"                  ! root200^
                                                end

  addServlet(classOf[PointsServlet], "/*")

  def root200 = get("/") {
    status must_== 200
  }
}
