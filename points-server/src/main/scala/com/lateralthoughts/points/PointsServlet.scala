package com.lateralthoughts.points

import com.lateralthoughts.points.controllers.{RewardingActionCategoryController, RewardingActionController}
import com.lateralthoughts.points.services.{RewardingActionCategoryService, RewardingActionService}

class PointsServlet(env: {
  val rewardingActionService: RewardingActionService
  val rewardingActionCategoryService: RewardingActionCategoryService
}) extends PointsStack with RewardingActionController with RewardingActionCategoryController {

  val rewardingActionService = env.rewardingActionService
  val rewardingActionCategoryService = env.rewardingActionCategoryService

  get("/") {
    "coucou"
  }


}
