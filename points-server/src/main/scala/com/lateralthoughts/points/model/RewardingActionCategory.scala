package com.lateralthoughts.points.model

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

/**
 * The category of rewarding action, for instance management, accountancy, public relation...
 *
 * @param id the id of the action, used in database
 * @param name the name of the category, for instance "accountancy"
 * @param description a description of the category, for instance "everything that implies accountancy : answer to the accountant..."
 * @param createdAt the date of the creation of the category, in UTC timezone
 * @param updatedAt the date of the last update of the category, in UTC timezone
 */
case class RewardingActionCategory(id: UUID,
                                   name: String,
                                   description: String,
                                   createdAt: OffsetDateTime,
                                   updatedAt: OffsetDateTime) extends Record

case class RewardingActionCategoryInput(id: Option[UUID],
                                        name: Option[String],
                                        description: Option[String]) extends Input[RewardingActionCategory] {

  override def generate = {

    val missingFields = List(
      retrieveFieldNameIfNotSet(this.name, "name"),
      retrieveFieldNameIfNotSet(this.description, "description")
    ).flatten

    if (missingFields.isEmpty) {
      val id = UUID.randomUUID()
      val name = this.name.get
      val description = this.description.get
      val createdAt = OffsetDateTime.now(Clock.systemUTC())
      val updatedAt = OffsetDateTime.now(Clock.systemUTC())
      Right(RewardingActionCategory(id, name, description, createdAt, updatedAt))
    } else {
      Left(generateMissingFieldsErrorMessage(missingFields))
    }
  }

  override def update(base:RewardingActionCategory) = {
    val id = base.id
    val name = pick(this.name, base.name)
    val description = pick(this.description, base.description)
    val createdAt = base.createdAt
    val updatedAt = OffsetDateTime.now(Clock.systemUTC())
    RewardingActionCategory(id, name.field, description.field, createdAt, updatedAt)
  }

}
