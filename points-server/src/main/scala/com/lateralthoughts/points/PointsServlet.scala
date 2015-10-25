package com.lateralthoughts.points

import com.lateralthoughts.points.controllers.RewardingActionController

class PointsServlet extends PointsStack with RewardingActionController {

  get("/") {
    "coucou"
  }



}
