package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.PointsServlet
import com.lateralthoughts.points.model.{JsonFormatter, RewardingAction}
import org.json4s.jackson.JsonMethods
import org.scalatest.FunSuiteLike
import org.scalatra.test.scalatest._

class RewardingActionControllerTest extends ScalatraSuite with FunSuiteLike with JsonFormatter {

  addServlet(classOf[PointsServlet], "/*")

  test("should retrieve list of available rewarding actions") {
    get("/action/") {
      status should equal(200)
      val listOfRewardingActions = JsonMethods.parse(body).extract[List[RewardingAction]]

    }
  }

  test("should return bad request when body is empty") {
    post("/action/") {
      status should equal (400)
      body should equal ("The request body is not a valid JSON object")
    }
  }

}
