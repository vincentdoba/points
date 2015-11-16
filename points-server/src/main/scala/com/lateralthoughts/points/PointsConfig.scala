package com.lateralthoughts.points

import com.lateralthoughts.points.repositories.{RewardingActionCategoryRepository, RewardingActionRepository}
import com.lateralthoughts.points.services.RewardingActionService

/**
  * Contains the injected beans of application
  */
object PointsConfig {

  lazy val rewardingActionRepository = RewardingActionRepository
  lazy val rewardingActionCategoryRepository = RewardingActionCategoryRepository

  lazy val rewardingActionService = new RewardingActionService(this)

}
