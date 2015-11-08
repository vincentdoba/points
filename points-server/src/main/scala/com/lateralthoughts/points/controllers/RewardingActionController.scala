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

  private def retrieveRewardingAction(actionId: UUID) = ok(rewardingActionService.retrieveRewardingAction(actionId))

  private def createNewRewardingAction(actionId: NewRewardingActionInput) = created(rewardingActionService.saveRewardingAction(actionId))

  private def updateRewardingAction(actionId: UUID) = retrievePostedJsonAnd(updateRewardingActionWithJson(actionId), "rewardingAction")(request)

  private def deleteRewardingAction(actionId: UUID) = noContent(rewardingActionService.deleteRewardingAction(actionId))

  private def retrieveActionIdFromURLAnd(f: UUID => ActionResult)(implicit request: HttpServletRequest) = retrieveUUIDFromURL(params(actionId))(f)

  private def updateRewardingActionWithJson(actionId: UUID)(input: UpdateRewardingActionInput) = ok(rewardingActionService.updateRewardingAction(actionId)(input))

}
