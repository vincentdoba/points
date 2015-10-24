package com.lateralthoughts.points

import com.lateralthoughts.points.endpoints.RewardingActionServlet

class PointsServlet extends PointsStack with RewardingActionServlet {

  get("/") {
    "coucou"
  }



}
