package com.lateralthoughts.points.controllers

import java.util.UUID
import javax.servlet.http.HttpServletRequest

import com.lateralthoughts.points.controllers.handlers.{HandlingJson, HandlingUUID}
import com.lateralthoughts.points.services.RewardingActionCategoryService
import org.scalatra.ActionResult

trait RewardingActionCategoryController extends HandlingJson with HandlingUUID with Controller {

  val actionCategoryId = "categoryId"
  val actionCategoryEndpoint = "actions/categories"

  val rewardingActionCategoryService: RewardingActionCategoryService

  get(s"/$actionCategoryEndpoint/") {
    retrieveAllRewardingActionCategories()
  }

  get(s"/$actionCategoryEndpoint/:$actionCategoryId") {
    retrieveCategoryIdFromURLAnd(retrieveRewardingActionCategory)
  }

  private def retrieveAllRewardingActionCategories() = ok(rewardingActionCategoryService.retrieveAll())

  private def retrieveRewardingActionCategory(categoryId: UUID) = ok(rewardingActionCategoryService.retrieve(categoryId))

  private def retrieveCategoryIdFromURLAnd(f: UUID => ActionResult)(implicit request: HttpServletRequest) = retrieveUUIDFromURL(params(actionCategoryId))(f)

}
