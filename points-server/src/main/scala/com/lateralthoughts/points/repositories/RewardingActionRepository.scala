package com.lateralthoughts.points.repositories

import com.lateralthoughts.points.model.records.RewardingAction

trait RewardingActionRepository extends Repository[RewardingAction]

object RewardingActionRepository extends RewardingActionRepository
