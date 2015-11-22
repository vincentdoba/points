package com.lateralthoughts.points.services

import java.util.UUID

import com.lateralthoughts.points.model.inputs.{NewRewardingActionCategoryInput, UpdateRewardingActionCategoryInput}
import com.lateralthoughts.points.model.records.RewardingActionCategory
import com.lateralthoughts.points.model.{ApplicationError, DatabaseError, RecordNotFound}
import com.lateralthoughts.points.repositories.Repository

import scala.util.{Failure, Success}

class RewardingActionCategoryService (env: {
  val rewardingActionCategoryRepository: Repository[RewardingActionCategory]
}) extends Service[RewardingActionCategory] {

  val repository = env.rewardingActionCategoryRepository

  def create(input: NewRewardingActionCategoryInput): Either[ApplicationError, RewardingActionCategory] = {
    save(input.generate)
  }

  def update(actionId: UUID)(input: UpdateRewardingActionCategoryInput): Either[ApplicationError, RewardingActionCategory] = repository.retrieve(actionId) match {
    case None => Left(ApplicationError(RecordNotFound, s"No rewarding action category with id $actionId found"))
    case Some(rewardingActionCategory) => save(input.update(rewardingActionCategory))
  }

  private def save(category: RewardingActionCategory): Either[ApplicationError, RewardingActionCategory] = {
    repository.save(category) match {
        case Success(savedCategory) => Right(savedCategory)
        case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
      }
  }

}
