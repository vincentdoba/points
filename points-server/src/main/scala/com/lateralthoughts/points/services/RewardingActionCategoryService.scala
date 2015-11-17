package com.lateralthoughts.points.services

import com.lateralthoughts.points.model.records.RewardingActionCategory
import com.lateralthoughts.points.repositories.Repository

class RewardingActionCategoryService (env: {
  val rewardingActionCategoryRepository: Repository[RewardingActionCategory]
}) extends Service[RewardingActionCategory] {

  val repository = env.rewardingActionCategoryRepository

}
