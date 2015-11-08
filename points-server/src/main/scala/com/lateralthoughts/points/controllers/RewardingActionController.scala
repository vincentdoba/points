package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID
import javax.servlet.http.HttpServletRequest

import com.lateralthoughts.points.controllers.handlers.{HandlingUUID, HandlingJson}
import com.lateralthoughts.points.model.inputs.{NewRewardingActionInput, UpdateRewardingActionInput}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.model.{ApplicationError, UUIDNotValid}
import com.lateralthoughts.points.services.RewardingActionService
import org.scalatra.ActionResult

import scala.util.{Failure, Success, Try}

trait RewardingActionController extends HandlingJson with HandlingUUID with Controller {

  val actionId = "actionId"

  val rewardingActionService = RewardingActionService

  get("/actions/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  get(s"/actions/:$actionId") {
    retrieveActionIdFromURLAnd(retrieveRewardingAction)
  }

  post("/actions/") {
    retrievePostedJsonAnd(createNewRewardingAction, "rewardingAction")(request)
  }

  put(s"/actions/:$actionId") {
    retrieveActionIdFromURLAnd(updateRewardingAction)
  }

  private def retrieveRewardingAction(input:UUID) = ok(rewardingActionService.retrieveRewardingAction(input))

  private def createNewRewardingAction(input:NewRewardingActionInput) = created(rewardingActionService.saveRewardingAction(input))

  private def updateRewardingAction(input:UUID) = retrievePostedJsonAnd(updateRewardingActionWithJson(input), "rewardingAction")(request)

  private def retrieveActionIdFromURLAnd(f: UUID => ActionResult)(implicit request: HttpServletRequest) = retrieveUUIDFromURLAnd(params(actionId))(f)

  private def updateRewardingActionWithJson(actionId:UUID)(input:UpdateRewardingActionInput) = ok(rewardingActionService.updateRewardingAction(actionId)(input))


}
