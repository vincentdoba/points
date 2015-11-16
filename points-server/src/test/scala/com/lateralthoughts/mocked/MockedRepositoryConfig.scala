package com.lateralthoughts.mocked

import com.lateralthoughts.points.services.RewardingActionService

object MockedRepositoryConfig {
  lazy val rewardingActionRepository = MockedRewardingActionRepository
  lazy val rewardingActionCategoryRepository = MockedRewardingActionCategoryRepository

  lazy val rewardingActionService = new RewardingActionService(this)

}
