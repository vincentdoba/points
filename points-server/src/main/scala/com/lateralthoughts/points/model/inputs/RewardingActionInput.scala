package com.lateralthoughts.points.model.inputs

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}

sealed trait RewardingActionInput extends Input[RewardingAction]

case class NewRewardingActionInput(name: String,
                                   category: InnerRewardingActionCategoryInput,
                                   description: String,
                                   points: Int) extends SaveInput[RewardingAction] with RewardingActionInput {

  def generate(category: RewardingActionCategory): RewardingAction = {

    val id = UUID.randomUUID()
    val name = this.name
    val description = this.description
    val points = this.points
    val createdAt = OffsetDateTime.now(Clock.systemUTC())
    val updatedAt = OffsetDateTime.now(Clock.systemUTC())
    RewardingAction(id, name, category, description, points, createdAt, updatedAt)

  }
}

case class UpdateRewardingActionInput(name: Option[String],
                                      category: Option[InnerRewardingActionCategoryInput],
                                      description: Option[String],
                                      points: Option[Int]) extends UpdateInput[RewardingAction] with RewardingActionInput {

  def update(rewardingAction: RewardingAction, category: RewardingActionCategory) = {
    val id = rewardingAction.id
    val name = pick(this.name, rewardingAction.name)
    val description = pick(this.description, rewardingAction.description)
    val points = pick(this.points, rewardingAction.points)
    val createdAt = rewardingAction.createdAt
    val updatedAt = updateDate(rewardingAction, Seq(name, description, points))
    RewardingAction(id, name.field, category, description.field, points.field, createdAt, updatedAt)
  }
}