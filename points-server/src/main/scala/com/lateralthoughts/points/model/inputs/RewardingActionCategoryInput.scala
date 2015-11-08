package com.lateralthoughts.points.model.inputs

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

import com.lateralthoughts.points.model.records.{RewardingAction, RewardingActionCategory}

sealed trait RewardingActionCategoryInput

case class NewRewardingActionCategoryInput(name: String,
                                           description: String) extends SaveInput[RewardingActionCategory] with RewardingActionCategoryInput {

  def generate = {
    val id = UUID.randomUUID()
    val name = this.name
    val description = this.description
    val createdAt = OffsetDateTime.now(Clock.systemUTC())
    val updatedAt = OffsetDateTime.now(Clock.systemUTC())
    RewardingActionCategory(id, name, description, createdAt, updatedAt)

  }

}

case class UpdateRewardingActionCategoryInput(name: Option[String],
                                              description: Option[String]) extends UpdateInput[RewardingActionCategory] with UpdatableRewardingActionCategoryInput with RewardingActionCategoryInput {


}

case class InnerRewardingActionCategoryInput(id: UUID,
                                             override val name: Option[String],
                                             override val description: Option[String])
  extends UpdatableRewardingActionCategoryInput
  with InnerInput[RewardingActionCategory]
  with RewardingActionCategoryInput {

  def create:Either[String, RewardingActionCategory] = {
    val missingFields = Seq(retrieveFieldNameIfNotSet(name, "name"), retrieveFieldNameIfNotSet(name, "description")).flatten

    if (missingFields.isEmpty) {
      Right(RewardingActionCategory(id, name.get, description.get, OffsetDateTime.now(Clock.systemUTC()), OffsetDateTime.now(Clock.systemUTC())))
    } else {
      Left(generateMissingFieldsErrorMessage(missingFields))
    }
  }

}

trait UpdatableRewardingActionCategoryInput extends UpdateInput[RewardingActionCategory] {
  val name:Option[String]
  val description:Option[String]

  override def update(base: RewardingActionCategory) = {
    val id = base.id
    val name = pick(this.name, base.name)
    val description = pick(this.description, base.description)
    val createdAt = base.createdAt
    val updatedAt = updateDate(base, Seq(name, description))
    RewardingActionCategory(id, name.field, description.field, createdAt, updatedAt)
  }
}