package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID
import javax.servlet.http.HttpServletRequest

import com.lateralthoughts.points.controllers.handlers.{HandlingJson, HandlingUUID}
import com.lateralthoughts.points.model.inputs.{NewRewardingActionInput, UpdateRewardingActionInput}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.services.RewardingActionService
import org.scalatra.{Ok, ActionResult}

trait RewardingActionController extends HandlingJson with HandlingUUID with Controller {

  val actionId = "actionId"
  val actionEndpoint = "actions"

  val rewardingActionService = RewardingActionService

  get(s"/$actionEndpoint/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  get(s"/$actionEndpoint/:$actionId") {
    retrieveActionIdFromURLAnd(retrieveRewardingAction)
  }

  post(s"/$actionEndpoint/") {
    retrievePostedJsonAnd(createNewRewardingAction, "rewardingAction")(request)
  }

  put(s"/$actionEndpoint/:$actionId") {
    retrieveActionIdFromURLAnd(updateRewardingAction)
  }

  delete(s"/$actionEndpoint/:$actionId") {
    retrieveActionIdFromURLAnd(x => Ok())
  }

  private def retrieveRewardingAction(input: UUID) = ok(rewardingActionService.retrieveRewardingAction(input))

  private def createNewRewardingAction(input: NewRewardingActionInput) = created(rewardingActionService.saveRewardingAction(input))

  private def updateRewardingAction(input: UUID) = retrievePostedJsonAnd(updateRewardingActionWithJson(input), "rewardingAction")(request)

  private def retrieveActionIdFromURLAnd(f: UUID => ActionResult)(implicit request: HttpServletRequest) = retrieveUUIDFromURL(params(actionId))(f)

  private def updateRewardingActionWithJson(actionId: UUID)(input: UpdateRewardingActionInput) = ok(rewardingActionService.updateRewardingAction(actionId)(input))

}
