package com.lateralthoughts.mocked

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import com.lateralthoughts.points.repositories.RewardingActionRepository

import scala.collection.mutable
import scala.util.Success

object MockedRewardingActionRepository extends RewardingActionRepository {

  private val category1: RewardingActionCategory = RewardingActionCategory(UUID.fromString("6220b616-990c-4961-9876-05cfded83552"), "category 1", "none", OffsetDateTime.now(), OffsetDateTime.now())
  private val category2: RewardingActionCategory = RewardingActionCategory(UUID.fromString("e9b2652e-4ecb-43fb-84de-82a841c7225d"), "category 2", "none", OffsetDateTime.now(), OffsetDateTime.now())

  private val rewardingAction1 = RewardingAction(UUID.fromString("1210955b-27b1-40c2-9d33-81601fbcfc31"), "first rewarding action", category1, "--", 1, OffsetDateTime.now(), OffsetDateTime.now())
  private val rewardingAction2 = RewardingAction(UUID.fromString("a6c31aff-a227-403d-9ace-f90ef993262d"), "second rewarding action", category1, "--", 0, OffsetDateTime.now(), OffsetDateTime.now())
  private val rewardingAction3 = RewardingAction(UUID.fromString("466d6984-8d9f-44d0-a8a3-6c9aceb7223b"), "third rewarding action", category2, "--", 2, OffsetDateTime.now(), OffsetDateTime.now())

  private val values = mutable.HashMap[UUID, RewardingAction](
   rewardingAction1.id -> rewardingAction1,
   rewardingAction2.id -> rewardingAction2,
   rewardingAction3.id -> rewardingAction3

  )

  override def retrieve(id:UUID) = values.get(id)
  override def retrieve() = Success(values.values.toList)

}
