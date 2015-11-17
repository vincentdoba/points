package com.lateralthoughts.points.services

import com.lateralthoughts.points.model.{DatabaseError, ApplicationError}
import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.repositories.Repository

import scala.util.{Failure, Success}

class RewardingActionCategoryService (env: {
  val rewardingActionCategoryRepository: Repository[RewardingActionCategory]
}) {

  def retrieveAll(): Either[ApplicationError, List[RewardingActionCategory]] = env.rewardingActionCategoryRepository.retrieve() match {
    case Success(categories) => Right(categories)
    case Failure(exception) => Left(ApplicationError(DatabaseError, "Unable to retrieve list of rewarding actions category"))
  }
}
