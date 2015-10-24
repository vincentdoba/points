package com.lateralthoughts.points.endpoints

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

import com.lateralthoughts.points.model.{RewardingActionCategory, RewardingAction}

trait RewardingActionServlet extends HandlingJsonEndpoint {

  get("/action/") {
    val now: OffsetDateTime = OffsetDateTime.now()
    val rewardingActionCategory = RewardingActionCategory(UUID.randomUUID(), "myCategory", "my category description", now, now)
    RewardingAction(UUID.randomUUID(), "myAction", rewardingActionCategory, "my action description", 1, now, now)
  }

  post("/action/") {
    val uuid = UUID.randomUUID()
    val createdAt = OffsetDateTime.now(Clock.systemUTC())
    val modifiedAt = createdAt

  }

}
