package com.lateralthoughts.points.repositories

import java.util.UUID

import com.lateralthoughts.points.model.RewardingAction

import scala.util.Try

object RewardingActionRepository {

  def retrieve(id: UUID): Option[RewardingAction] = None

  def save(rewardingAction: RewardingAction): Try[RewardingAction] = Try {
    rewardingAction
  }


}
