package com.lateralthoughts.points.controllers

import java.util.UUID

import com.lateralthoughts.points.PointsServlet
import com.lateralthoughts.points.model.JsonFormatter
import com.lateralthoughts.points.model.records.RewardingAction
import org.json4s.jackson.JsonMethods
import org.scalatra.test.scalatest._

class RewardingActionControllerTest extends ScalatraSuite with ScalatraFlatSpec with JsonFormatter {

  addServlet(classOf[PointsServlet], "/*")

  "Calling get /actions/" should "retrieve list of available rewarding actions" in {
    get("/actions/") {
      status should equal(200)
      val listOfRewardingActions = JsonMethods.parse(body).extract[List[RewardingAction]]

    }
  }

  "Calling post /actions/" should "return bad request when body is empty" in {
    post("/actions/") {
      status should equal(400)
      body should equal( """{"code":"JsonNotValid","message":"The request body is not a valid JSON object"}""")
    }
  }

  it should "return bad request when body is not a rewarding action input" in {
    val json = "{}"

    post("/actions/", json.toCharArray.map(_.toByte)) {
      status should equal(400)
      body should equal( """{"code":"InputObjectNotValid","message":"The request body is not a JSON object representing rewardingAction"}""")
    }
  }

  it should "return created when a rewarding action is created" in {
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
      status should equal(201)
    }
  }

  it should "return bad request when body does not contain a valid category" in {
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
      status should equal(400)
      body should equal( """{"code":"InputObjectIncomplete","message":"Unable to create category due to : The following fields weren't correctly filled in the request : name, description"}""")
    }
  }

  "Calling put /actions/:actionId" should "return bad request when trying to update an action with a not valid action id" in {
    val fakeId = "myFakeId"

    put(s"/actions/$fakeId") {
      status should equal(400)
      body should equal( """{"code":"UUIDNotValid","message":"Invalid UUID string: myFakeId"}""")
    }

  }

}
