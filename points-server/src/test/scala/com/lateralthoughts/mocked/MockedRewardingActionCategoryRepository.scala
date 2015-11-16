package com.lateralthoughts.mocked

import java.time.OffsetDateTime
import java.util.UUID

import com.lateralthoughts.points.model.records.RewardingActionCategory
import com.lateralthoughts.points.repositories.RewardingActionCategoryRepository

import scala.collection.mutable
import scala.util.Success

object MockedRewardingActionCategoryRepository extends RewardingActionCategoryRepository {

  private val category1: RewardingActionCategory = RewardingActionCategory(UUID.fromString("6220b616-990c-4961-9876-05cfded83552"), "category 1", "none", OffsetDateTime.now(), OffsetDateTime.now())
  private val category2: RewardingActionCategory = RewardingActionCategory(UUID.fromString("e9b2652e-4ecb-43fb-84de-82a841c7225d"), "category 2", "none", OffsetDateTime.now(), OffsetDateTime.now())

  val values = mutable.HashMap[UUID, RewardingActionCategory](
    UUID.fromString("6220b616-990c-4961-9876-05cfded83552") -> category1,
    UUID.fromString("e9b2652e-4ecb-43fb-84de-82a841c7225d") -> category2
  )

  override def retrieve(id:UUID) = values.get(id)
  override def retrieve() = Success(values.values.toList)

}

