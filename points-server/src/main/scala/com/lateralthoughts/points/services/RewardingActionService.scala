package com.lateralthoughts.points.services

import java.util.UUID

import com.lateralthoughts.points.model._
import com.lateralthoughts.points.model.inputs.{InnerRewardingActionCategoryInput, NewRewardingActionInput, RewardingActionInput, UpdateRewardingActionInput}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.repositories.Repository

import scala.util.{Failure, Success}

class RewardingActionService(env: {
  val rewardingActionRepository: Repository[RewardingAction]
  val rewardingActionCategoryRepository: Repository[RewardingActionCategory]
}) extends Service[RewardingAction] {

  val repository = env.rewardingActionRepository

  def create(input: NewRewardingActionInput): Either[ApplicationError, RewardingAction] = {
    retrieveCategory(input).right.flatMap[ApplicationError, RewardingAction](x => save(input.generate(x)))
  }

  def update(actionId: UUID)(input: UpdateRewardingActionInput): Either[ApplicationError, RewardingAction] = repository.retrieve(actionId) match {
    case None => Left(ApplicationError(RecordNotFound, s"No rewarding action with id $actionId found"))
    case Some(rewardingAction) => retrieveCategory(input, Some(rewardingAction)).right.flatMap[ApplicationError, RewardingAction](x => save(input.update(rewardingAction, x)))
  }

  private def retrieveCategory(rewardingActionInput: RewardingActionInput, rewardingAction: Option[RewardingAction] = None): Either[ApplicationError, RewardingActionCategory] = rewardingActionInput match {
    case NewRewardingActionInput(_, category, _, _) => retrieveCategoryFromInput(category)
    case UpdateRewardingActionInput(_, optionalCategory, _, _) => optionalCategory match {
      case None => Right(rewardingAction.map(_.category).get) // rewardingAction.category should never be None at this level
      case Some(category) => retrieveCategoryFromInput(category)
    }
  }

  private def retrieveCategoryFromInput(category: InnerRewardingActionCategoryInput): Either[ApplicationError, RewardingActionCategory] = env.rewardingActionCategoryRepository.retrieve(category.id) match {
    case Some(retrievedCategory) => Right(category.update(retrievedCategory))
    case None => category.create.left.map[ApplicationError](x => ApplicationError(InputObjectIncomplete, "Unable to create category due to : " + x.message))
  }

  private def save(rewarding: RewardingAction): Either[ApplicationError, RewardingAction] = {
    env.rewardingActionCategoryRepository.save(rewarding.category) match {
      case Success(_) => repository.save(rewarding) match {
        case Success(savedRewarding) => Right(savedRewarding)
        case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
      }
      case Failure(exception) => Left(ApplicationError(DatabaseError, "Unable to save rewarding action category due to : " + exception.getMessage))
    }
  }
}
