package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.model.JsonFormatter
import com.lateralthoughts.points.model.records.RewardingActionCategory
import org.json4s.jackson.JsonMethods
import org.scalatra.test.scalatest._

class RewardingActionCategoryControllerTest extends ScalatraSuite with InitServlet with JsonFormatter {

  "Calling get /actions/categories" should "retrieve list of available rewarding action categories" in {
    get("/actions/categories/") {
      status should equal(200)
      val listOfRewardingActionCategories = JsonMethods.parse(body).extract[List[RewardingActionCategory]]
      listOfRewardingActionCategories should have length 2

    }
  }

  "Calling get /actions/categories/:categoryId" should "return bad request when category id is not valid" in {
    val notValidId = "notValidId"

    get(s"/actions/categories/$notValidId") {
      status should equal(400)
      body should equal( s"""{"code":"UUIDNotValid","message":"Invalid UUID string: $notValidId"}""")
    }
  }

  it should "return not found when category is not found" in {
    val nonExistentCategoryId = "00000000-0000-0000-0000-000000000000"

    get(s"/actions/categories/$nonExistentCategoryId") {
      status should equal(404)
      body should equal( s"""{"code":"RecordNotFound","message":"No element with id $nonExistentCategoryId found"}""")
    }
  }

  it should "return a rewarding action category when category is found" in {
    val existentCategoryId = "6220b616-990c-4961-9876-05cfded83552"

    get(s"/actions/categories/$existentCategoryId") {
      status should equal(200)
      val rewardingAction = JsonMethods.parse(body).extract[RewardingActionCategory]
      rewardingAction.id.toString should equal(existentCategoryId)
    }
  }

  "Calling post /actions/categories" should "return bad request when body is empty" in {
    post("/actions/categories/") {
      status should equal(400)
      body should equal( """{"code":"JsonNotValid","message":"The request body is not a valid JSON object"}""")
    }
  }

  it should "return bad request when body is not a rewarding action category input" in {
    val json = "{}"

    post("/actions/categories/", json.toCharArray.map(_.toByte)) {
      status should equal(400)
      body should equal( """{"code":"InputObjectNotValid","message":"The request body is not a JSON object representing rewardingActionCategory"}""")
    }
  }

  it should "return created when a rewarding action category is created" in {
    val json =
      s"""
        {
            "name":"myCategory",
            "description":"Description of my category"
        }
      """.stripMargin

    post("/actions/categories/", json.toCharArray.map(_.toByte)) {
      status should equal(201)
      val createdCategory = JsonMethods.parse(body).extract[RewardingActionCategory]
      createdCategory.name should equal("myCategory")
      createdCategory.description should equal("Description of my category")
    }
  }

}
