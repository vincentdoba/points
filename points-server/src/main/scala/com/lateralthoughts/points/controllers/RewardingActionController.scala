package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.{RewardingAction, RewardingActionCategory, RewardingActionInput}
import com.lateralthoughts.points.repositories.RewardingActionRepository
import org.json4s.jackson.JsonMethods
import org.scalatra.{InternalServerError, BadRequest, Ok}

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
            case Left(fields) => BadRequest("The following fields weren't filled in the request : " + fields.reduce((a, b) => a + ", " + b))
            case Right(rewarding) => save(rewarding)
          }
          case Some(rewarding) => save(rewarding)
        }
      }
    }

  }

  private def save(rewarding: RewardingAction) = {
    RewardingActionRepository.save(rewarding) match {
      case Success(savedRewarding) => Ok(savedRewarding)
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }
}
