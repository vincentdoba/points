package com.lateralthoughts.points.services

import java.util.UUID

import com.lateralthoughts.points.model.inputs.{InnerRewardingActionCategoryInput, NewRewardingActionInput, RewardingActionInput, UpdateRewardingActionInput}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.model._
import com.lateralthoughts.points.repositories.{RewardingActionCategoryRepository, RewardingActionRepository}

import scala.util.{Failure, Success}

object RewardingActionService {

  val rewardingActionRepository = RewardingActionRepository
  val rewardingActionCategoryRepository = RewardingActionCategoryRepository

  def saveRewardingAction(input: NewRewardingActionInput): Either[ApplicationError, RewardingAction] = {
    retrieveCategory(input).right.flatMap[ApplicationError, RewardingAction](x => save(input.generate(x)))
  }

  def updateRewardingAction(actionId: UUID)(input: UpdateRewardingActionInput): Either[ApplicationError, RewardingAction] = {
    Left(ApplicationError(NotCoded, "Not Implemented"))
  }

  def retrieveRewardingAction(actionId:UUID): Either[ApplicationError, RewardingAction] = rewardingActionRepository.retrieve(actionId) match {
    case None => Left(ApplicationError(RecordNotFound, s"No rewarding action with id $actionId found"))
    case Some(rewardingAction) => Right(rewardingAction)
  }

  def deleteRewardingAction(actionId:UUID): Either[ApplicationError, String] = rewardingActionRepository.delete(actionId) match {
    case Success(message) => Right(message)
    case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
  }

  private def save(rewarding: RewardingAction): Either[ApplicationError, RewardingAction] = {
    rewardingActionCategoryRepository.save(rewarding.category) match {
      case Success(_) => rewardingActionRepository.save(rewarding) match {
        case Success(savedRewarding) => Right(savedRewarding)
        case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
      }
      case Failure(exception) => Left(ApplicationError(DatabaseError, "Unable to save rewarding action category due to : " + exception.getMessage))
    }

  }

  private def retrieveCategory(rewardingActionInput: RewardingActionInput, rewardingAction: Option[RewardingAction] = None): Either[ApplicationError, RewardingActionCategory] = rewardingActionInput match {
    case NewRewardingActionInput(_, category, _, _) => retrieveCategoryFromInput(category)
    case UpdateRewardingActionInput(_, optionalCategory, _, _) => optionalCategory match {
      case None => Right(rewardingAction.map(_.category).get) // rewardingAction.category should never be None at this level
      case Some(category) => retrieveCategoryFromInput(category)
    }
  }

  private def retrieveCategoryFromInput(category: InnerRewardingActionCategoryInput): Either[ApplicationError, RewardingActionCategory] = {
    rewardingActionCategoryRepository.retrieve(category.id) match {
      case Some(retrievedCategory) => Right(category.update(retrievedCategory))
      case None => category.create match {
        case Left(message) => Left(ApplicationError(InputObjectIncomplete, s"Unable to create category due to : $message"))
        case Right(createdCategory) => Right(createdCategory)
      }
    }
  }

}
