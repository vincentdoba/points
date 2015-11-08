package com.lateralthoughts.points.model.inputs

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}
import org.scalatest.{FlatSpec, Matchers}

class UpdateRewardingActionInputTest extends FlatSpec with Matchers {

  val rewardingActionCategoryId = "C56A4180-65AA-42EC-A945-5FD21DEC0538"

  val rewardingActionCategory = RewardingActionCategory(UUID.fromString(rewardingActionCategoryId), "categoryName", "categoryDescription", OffsetDateTime.now(Clock.systemUTC()), OffsetDateTime.now(Clock.systemUTC()))

  val createdAt = OffsetDateTime.now(Clock.systemUTC()).minusDays(2)
  val updatedAt = OffsetDateTime.now(Clock.systemUTC()).minusDays(1)
  val id: UUID = UUID.randomUUID()

  val rewardingAction = RewardingAction(id, "name", rewardingActionCategory, "description", 2, createdAt, updatedAt)

  it should "update rewarding action with new input elements" in {
    // Given
    val rewardingActionInput = UpdateRewardingActionInput(Some("newName"), None, Some("newDescription"), Some(3))


    // When
    val updatedRewardingAction = rewardingActionInput.update(rewardingAction, rewardingActionCategory)

    // Then
    updatedRewardingAction.id should be (id)
    updatedRewardingAction.name should be ("newName")
    updatedRewardingAction.category should be (rewardingActionCategory)
    updatedRewardingAction.description should be ("newDescription")
    updatedRewardingAction.points should be (3)
    updatedRewardingAction.createdAt should be (createdAt)
    updatedRewardingAction.updatedAt.isAfter(updatedAt) should be (true)

  }

  it should "not change rewarding action when input elements are the same than the one saved" in {
    // Given
    val rewardingActionInput = UpdateRewardingActionInput(Some("name"), None, Some("description"), Some(2))

    // When
    val updatedRewardingAction = rewardingActionInput.update(rewardingAction, rewardingActionCategory)

    // Then
    updatedRewardingAction.id should be (id)
    updatedRewardingAction.name should be ("name")
    updatedRewardingAction.category should be (rewardingActionCategory)
    updatedRewardingAction.description should be ("description")
    updatedRewardingAction.points should be (2)
    updatedRewardingAction.createdAt should be (createdAt)
    updatedRewardingAction.updatedAt should be (updatedAt)

  }

  it should "not change rewarding action when there are no new input elements" in {
    // Given
    val rewardingActionInput = UpdateRewardingActionInput(None, None, None, None)

    // When
    val updatedRewardingAction = rewardingActionInput.update(rewardingAction, rewardingActionCategory)

    // Then
    updatedRewardingAction.id should be (id)
    updatedRewardingAction.name should be ("name")
    updatedRewardingAction.category should be (rewardingActionCategory)
    updatedRewardingAction.description should be ("description")
    updatedRewardingAction.points should be (2)
    updatedRewardingAction.createdAt should be (createdAt)
    updatedRewardingAction.updatedAt should be (updatedAt)

  }

}
