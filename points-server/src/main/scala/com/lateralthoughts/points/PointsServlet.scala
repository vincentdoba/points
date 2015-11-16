package com.lateralthoughts.points

import com.lateralthoughts.points.controllers.RewardingActionController
import com.lateralthoughts.points.services.RewardingActionService

class PointsServlet(env: {val rewardingActionService: RewardingActionService}) extends PointsStack with RewardingActionController {

  val rewardingActionService = env.rewardingActionService

  get("/") {
    "coucou"
  }


}
