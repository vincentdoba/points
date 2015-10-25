package com.lateralthoughts.points.controllers

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

import com.lateralthoughts.points.model.{RewardingAction, RewardingActionCategory}

trait RewardingActionController extends HandlingJson {

  get("/action/") {
    val now = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    List(RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now))
  }

  post("/action/") {
    val uuid = UUID.randomUUID()
    val createdAt = OffsetDateTime.now(Clock.systemUTC())
    val modifiedAt = createdAt
    //params("")

  }

}
