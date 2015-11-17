package com.lateralthoughts.points.controllers

import com.lateralthoughts.points.controllers.handlers.{HandlingError, HandlingJson}

trait RewardingActionCategoryController extends HandlingJson with HandlingError {

  val actionCategoryId = "categoryId"
  val actionCategoryEndpoint = "actions/categories/"

}
