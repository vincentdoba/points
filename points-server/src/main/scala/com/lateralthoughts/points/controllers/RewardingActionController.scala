package com.lateralthoughts.points.controllers

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.inputs.{InnerRewardingActionCategoryInput, UpdateRewardingActionInput, RewardingActionInput, NewRewardingActionInput}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.repositories.{RewardingActionCategoryRepository, RewardingActionRepository}
import org.scalatra._

import scala.util.{Try, Failure, Success}

trait RewardingActionController extends HandlingJson {

  get("/actions/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  post("/actions/") {
    retrievePostedJsonAnd(saveRewardingAction, "rewardingAction")(request)
  }

  put("/actions/:actionId") {
    Try {
      UUID.fromString(params("actionId"))
    } match {
      case Success(actionId) => retrievePostedJsonAnd(updateRewardingAction(actionId), "rewardingAction")(request)
      case Failure(e) => BadRequest(e.getMessage)
    }
  }

  def saveRewardingAction(input: NewRewardingActionInput) = {
    retrieveCategory(input) match {
      case Left(x) => x
      case Right(rewardingActionCategory) => save(input.generate(rewardingActionCategory), Created.apply)
    }
  }

  def updateRewardingAction(actionId: UUID)(input: UpdateRewardingActionInput) = {
    NotImplemented("Not yet implemented")
  }

  private def save(rewarding: RewardingAction, successStatus: (Any, Map[String, String], String) => ActionResult): ActionResult = {
    RewardingActionRepository.save(rewarding) match {
      case Success(savedRewarding) => successStatus(savedRewarding, Map.empty, "")
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }

  private def retrieveCategory(rewardingActionInput: RewardingActionInput, rewardingAction: Option[RewardingAction] = None): Either[ActionResult, RewardingActionCategory] = rewardingActionInput match {
    case NewRewardingActionInput(_, category, _, _) => retrieveCategoryFromInput(category)
    case UpdateRewardingActionInput(_, optionalCategory, _, _) => optionalCategory match {
      case None => Right(rewardingAction.map(_.category).get) // Should never be None at this level
      case Some(category) => retrieveCategoryFromInput(category)
    }
  }

  private def retrieveCategoryFromInput(category: InnerRewardingActionCategoryInput): Either[ActionResult, RewardingActionCategory] = {
    RewardingActionCategoryRepository.retrieve(category.id) match {
      case Some(retrievedCategory) => Right(category.update(retrievedCategory))
      case None => category.create match {
        case Left(message) => Left(BadRequest(s"unable to create category due to : $message"))
        case Right(createdCategory) => Right(createdCategory)
      }
    }
  }
}
