package com.lateralthoughts.points.controllers

import java.util.UUID

import com.lateralthoughts.points.PointsServlet
import com.lateralthoughts.points.model.JsonFormatter
import com.lateralthoughts.points.model.records.RewardingAction
import org.json4s.jackson.JsonMethods
import org.scalatest.FunSuiteLike
import org.scalatra.test.scalatest._

class RewardingActionControllerTest extends ScalatraSuite with FunSuiteLike with JsonFormatter {

  addServlet(classOf[PointsServlet], "/*")

  test("should retrieve list of available rewarding actions") {
    get("/actions/") {
      status should equal(200)
      val listOfRewardingActions = JsonMethods.parse(body).extract[List[RewardingAction]]

    }
  }

  test("should return bad request when body is empty") {
    post("/actions/") {
      status should equal (400)
      body should equal ("The request body is not a valid JSON object")
    }
  }

  test("should return bad request when body is not a rewarding action input") {
    val json = "{}"

    post("/actions/", json.toCharArray.map(_.toByte)) {
      status should equal (400)
      body should equal ("The request body is not a JSON object representing rewardingAction")
    }
  }

  test("should return created when a rewarding action is created") {
    val categoryId = UUID.randomUUID()
    val json =
      s"""
        {
          "name":"myAction",
          "category": {
            "id":"$categoryId",
            "name":"myCategory",
            "description":"Description of my category"
          },
          "description":"Description of my action",
          "points":1
        }
      """.stripMargin

    post("/actions/", json.toCharArray.map(_.toByte)) {
      status should equal (201)
    }
  }

  test("should return bad request when body does not contain a valid category") {
    val categoryId = UUID.randomUUID()
    val json =
      s"""
        {
          "name":"myAction",
          "category": {
            "id":"$categoryId"
          },
          "description":"Description of my action",
          "points":1
        }
      """.stripMargin

    post("/actions/", json.toCharArray.map(_.toByte)) {
      status should equal (400)
      body should equal ("Unable to create category due to : The following fields weren't correctly filled in the request : name, description")
    }
  }

  test("should return bad request when trying to update an action with a not valid action id") {
    val fakeId = "myFakeId"

    put(s"/actions/$fakeId") {
      status should equal (400)
    }

  }

}
