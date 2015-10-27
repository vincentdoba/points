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

  test("should return bad request when body is not a rewarding action input") {
    val json = "{}"

    post("/action/", json.toCharArray.map(_.toByte)) {
      status should equal (400)
      body should equal ("The following fields weren't correctly filled in the request : name, category, description, points")
    }
  }

  test("should return created when a rewarding action is created") {
    val json =
      """
        {
          "name":"myAction",
          "category": {
            "name":"myCategory",
            "description":"Description of my category"
          },
          "description":"Description of my action",
          "points":1
        }
      """.stripMargin

    post("/action/", json.toCharArray.map(_.toByte)) {
      status should equal (201)
    }
  }

}
