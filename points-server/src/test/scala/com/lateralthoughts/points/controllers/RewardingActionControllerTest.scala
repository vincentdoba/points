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

  "Calling get /actions/:actionId" should "return bad request when action id is not valid" in {
    val notValidId = "notValidId"

    get(s"/actions/$notValidId") {
      status should equal(400)
      body should equal( s"""{"code":"UUIDNotValid","message":"Invalid UUID string: $notValidId"}""")
    }
  }

  it should "return not found when action is not found" in {
    val nonExistentActionId = "00000000-0000-0000-0000-000000000000"

    get(s"/actions/$nonExistentActionId") {
      status should equal(404)
      body should equal( s"""{"code":"RecordNotFound","message":"No rewarding action with id $nonExistentActionId found"}""")
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

  "Calling put /actions/:actionId" should "return bad request when trying to update an action with a not valid action id" in {
    val notValidId = "notValidId"

    put(s"/actions/$notValidId") {
      status should equal(400)
      body should equal( s"""{"code":"UUIDNotValid","message":"Invalid UUID string: $notValidId"}""")
    }

  }

  "Calling delete /actions/:actionId" should "return bad request when trying to delete an action with a not valid action id" in {
    val notValidId = "notValidId"

    delete(s"/actions/$notValidId") {
      status should equal(400)
      body should equal( s"""{"code":"UUIDNotValid","message":"Invalid UUID string: $notValidId"}""")
    }
  }

  it should "return no content when trying to delete an action that doesn't exist" in {
    val nonExistentActionId = "00000000-0000-0000-0000-000000000000"

    delete(s"/actions/$nonExistentActionId") {
      status should equal(204)
    }
  }

}
