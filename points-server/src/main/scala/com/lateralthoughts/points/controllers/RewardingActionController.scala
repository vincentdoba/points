package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.{RewardingAction, RewardingActionCategory, RewardingActionInput}
import com.lateralthoughts.points.repositories.RewardingActionRepository
import org.scalatra._

import scala.util.{Failure, Success}

trait RewardingActionController extends HandlingJson[RewardingActionInput] {

  override def typeName = "RewardingAction"

  get("/action/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  post("/action/") {
    retrievePostedJsonAnd(saveRewardingAction)(request)
  }

  def saveRewardingAction(input:RewardingActionInput) = {
    input.id.flatMap(RewardingActionRepository.retrieve).map(input.update) match {
      case None => input.generate match {
        case Left(message) => BadRequest(message)
        case Right(rewarding) => save(rewarding, Created.apply)
      }
      case Some(rewarding) => save(rewarding, Ok.apply)
    }
  }

  private def save(rewarding: RewardingAction, successStatus: (Any, Map[String, String], String) => ActionResult): ActionResult = {
    RewardingActionRepository.save(rewarding) match {
      case Success(savedRewarding) => successStatus(savedRewarding, Map.empty, "")
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }
}
