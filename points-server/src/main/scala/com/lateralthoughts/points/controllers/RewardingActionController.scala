package com.lateralthoughts.points.controllers

import java.util.UUID
import javax.servlet.http.HttpServletRequest

import com.lateralthoughts.points.controllers.handlers.{HandlingJson, HandlingUUID}
import com.lateralthoughts.points.model.inputs.{NewRewardingActionInput, UpdateRewardingActionInput}
import com.lateralthoughts.points.services.RewardingActionService
import org.scalatra.ActionResult

trait RewardingActionController extends HandlingJson with HandlingUUID with Controller {

  val actionId = "actionId"
  val actionEndpoint = "actions"

  val rewardingActionService = RewardingActionService

  get(s"/$actionEndpoint/") {
    retrieveAllRewardingActions()
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
    retrieveActionIdFromURLAnd(deleteRewardingAction)
  }

  private def retrieveAllRewardingActions() = ok(rewardingActionService.retrieveAllRewardingActions())

  private def retrieveRewardingAction(input: UUID) = ok(rewardingActionService.retrieveRewardingAction(input))

  private def createNewRewardingAction(input: NewRewardingActionInput) = created(rewardingActionService.saveRewardingAction(input))

  private def updateRewardingAction(input: UUID) = retrievePostedJsonAnd(updateRewardingActionWithJson(input), "rewardingAction")(request)

  private def deleteRewardingAction(input: UUID) = noContent(rewardingActionService.deleteRewardingAction(input))

  private def retrieveActionIdFromURLAnd(f: UUID => ActionResult)(implicit request: HttpServletRequest) = retrieveUUIDFromURL(params(actionId))(f)

  private def updateRewardingActionWithJson(actionId: UUID)(input: UpdateRewardingActionInput) = ok(rewardingActionService.updateRewardingAction(actionId)(input))

}
