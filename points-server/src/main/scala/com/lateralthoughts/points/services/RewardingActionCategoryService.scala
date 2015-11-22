package com.lateralthoughts.points.services

import com.lateralthoughts.points.model.inputs.NewRewardingActionCategoryInput
import com.lateralthoughts.points.model.records.RewardingActionCategory
import com.lateralthoughts.points.model.{ApplicationError, DatabaseError}
import com.lateralthoughts.points.repositories.Repository

import scala.util.{Failure, Success}

class RewardingActionCategoryService (env: {
  val rewardingActionCategoryRepository: Repository[RewardingActionCategory]
}) extends Service[RewardingActionCategory] {

  val repository = env.rewardingActionCategoryRepository

  def create(input: NewRewardingActionCategoryInput): Either[ApplicationError, RewardingActionCategory] = {
    save(input.generate)
  }

  private def save(category: RewardingActionCategory): Either[ApplicationError, RewardingActionCategory] = {
    repository.save(category) match {
        case Success(savedCategory) => Right(savedCategory)
        case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
      }
  }

}
