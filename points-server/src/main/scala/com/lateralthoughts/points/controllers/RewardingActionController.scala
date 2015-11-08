package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.inputs.{NewRewardingActionInput, UpdateRewardingActionInput}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.model.{ApplicationError, UUIDNotValid}
import com.lateralthoughts.points.services.RewardingActionService

import scala.util.{Failure, Success, Try}

trait RewardingActionController extends HandlingJson with Controller {

  val rewardingActionService = RewardingActionService

  get("/actions/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  get("/actions/:actionId") {
    Try {
      UUID.fromString(params("actionId"))
    } match {
      case Success(actionId) => ok(rewardingActionService.retrieveRewardingAction(actionId))
      case Failure(e) => buildErrorResponse(ApplicationError(UUIDNotValid, e.getMessage))
    }
  }

  post("/actions/") {
    retrievePostedJsonAnd(createNewRewardingAction, "rewardingAction")(request)
  }

  put("/actions/:actionId") {
    Try {
      UUID.fromString(params("actionId"))
    } match {
      case Success(actionId) => retrievePostedJsonAnd(updateRewardingAction(actionId), "rewardingAction")(request)
      case Failure(e) => buildErrorResponse(ApplicationError(UUIDNotValid, e.getMessage))
    }
  }

  private def createNewRewardingAction(input:NewRewardingActionInput) = created(rewardingActionService.saveRewardingAction(input))

  private def updateRewardingAction(actionId:UUID)(input:UpdateRewardingActionInput) = ok(rewardingActionService.updateRewardingAction(actionId)(input))


}
