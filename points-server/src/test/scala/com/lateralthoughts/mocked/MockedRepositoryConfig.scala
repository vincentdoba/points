package com.lateralthoughts.mocked

import com.lateralthoughts.points.services.{RewardingActionCategoryService, RewardingActionService}

object MockedRepositoryConfig {
  lazy val rewardingActionRepository = MockedRewardingActionRepository
  lazy val rewardingActionCategoryRepository = MockedRewardingActionCategoryRepository

  lazy val rewardingActionService = new RewardingActionService(this)
  lazy val rewardingActionCategoryService = new RewardingActionCategoryService(this)

}
