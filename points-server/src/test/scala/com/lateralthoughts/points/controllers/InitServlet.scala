package com.lateralthoughts.points.controllers

import com.lateralthoughts.mocked.MockedRepositoryConfig
import com.lateralthoughts.points.PointsServlet
import org.scalatra.test.scalatest.ScalatraFlatSpec

trait InitServlet extends ScalatraFlatSpec {

  addServlet(new PointsServlet(MockedRepositoryConfig), "/*")


}
