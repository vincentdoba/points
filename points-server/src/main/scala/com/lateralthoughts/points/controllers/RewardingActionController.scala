package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.{RewardingAction, RewardingActionCategory, RewardingActionInput}
import com.lateralthoughts.points.repositories.RewardingActionRepository
import org.json4s.jackson.JsonMethods
import org.scalatra._

import scala.util.{Failure, Success}

trait RewardingActionController extends HandlingJson {

  get("/action/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  post("/action/") {

    JsonMethods.parseOpt(request.body) match {
      case None => BadRequest("The request body is not a valid JSON object")
      case Some(json) => json.extractOpt[RewardingActionInput] match {
        case None => BadRequest("The request body is not a JSON object representing a Rewarding Action")
        case Some(input) => input.id.flatMap(RewardingActionRepository.retrieve).map(input.update) match {
          case None => input.generateRewardingAction match {
            case Left(message) => BadRequest(message)
            case Right(rewarding) => save(rewarding, Created.apply)
          }
          case Some(rewarding) => save(rewarding, Ok.apply)
        }
      }
    }

  }

  private def save(rewarding: RewardingAction, successStatus: (Any, Map[String, String], String) => ActionResult): Any = {
    RewardingActionRepository.save(rewarding) match {
      case Success(savedRewarding) => successStatus(savedRewarding, Map.empty, "")
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }
}
