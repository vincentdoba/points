package com.lateralthoughts.points.controllers

import java.util.UUID

import com.lateralthoughts.mocked.MockedRepositoryConfig
import com.lateralthoughts.points.{PointsConfig, PointsServlet}
import com.lateralthoughts.points.model.JsonFormatter
import com.lateralthoughts.points.model.records.RewardingAction
import org.json4s.jackson.JsonMethods
import org.scalatra.test.scalatest._

class RewardingActionControllerTest extends ScalatraSuite with ScalatraFlatSpec with JsonFormatter {

  addServlet(new PointsServlet(MockedRepositoryConfig), "/*")

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
      body should equal( s"""{"code":"RecordNotFound","message":"No element with id $nonExistentActionId found"}""")
    }
  }

  it should "return a rewarding action when action is found" in {
    val existentActionId = "1210955b-27b1-40c2-9d33-81601fbcfc31"

    get(s"/actions/$existentActionId") {
      status should equal(200)
      val rewardingAction = JsonMethods.parse(body).extract[RewardingAction]
      rewardingAction.id.toString should equal (existentActionId)
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

  it should "return bad request when body is empty" in {
    val actionId = "C56A4180-65AA-42EC-A945-5FD21DEC0538"

    put(s"/actions/$actionId") {
      status should equal(400)
      body should equal( """{"code":"JsonNotValid","message":"The request body is not a valid JSON object"}""")
    }
  }

  it should "return not found when rewarding action to be updated is not found" in {
    val nonExistentActionId = "00000000-0000-0000-0000-000000000000"
    val json = "{}"

    put(s"/actions/$nonExistentActionId", json.toCharArray.map(_.toByte)) {
      status should equal(404)
      body should equal( s"""{"code":"RecordNotFound","message":"No rewarding action with id $nonExistentActionId found"}""")
    }
  }

  it should "return ok when rewarding action is updated" in {
    val actionId = "1210955b-27b1-40c2-9d33-81601fbcfc31"
    val json = """{"description":"my new description"}"""

    put(s"/actions/$actionId", json.toCharArray.map(_.toByte)) {
      status should equal(200)
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

  it should "return no content when deleting an action" in {
    val nonExistentActionId = "a6c31aff-a227-403d-9ace-f90ef993262d"

    delete(s"/actions/$nonExistentActionId") {
      status should equal(204)
    }
  }

}
