package com.lateralthoughts.points.model

import java.time.{Clock, OffsetDateTime}
import java.util.UUID

/**
 * A rewarding action is an action for the company that gives the performer of the action some business point. Each six
 * months, the points owned by everyone are converted to money
 *
 * @param id the id of the action, used in database
 * @param name the name of the action, for instance "Post a small blog article"
 * @param category the category of the action
 * @param description a description of the action, for instance "You should post a blog article containing around 300 words"
 * @param points the number of points that will be given to the performer of the action, each time he/she performs the action
 * @param createdAt the date of the creation of the action, in UTC timezone
 * @param updatedAt the date of the last update of the action, in UTC timezone
 */
case class RewardingAction(id: UUID,
                           name: String,
                           category: RewardingActionCategory,
                           description: String,
                           points: Int,
                           createdAt: OffsetDateTime,
                           updatedAt: OffsetDateTime) extends Record {

}

case class RewardingActionInput(id: Option[UUID],
                                name: Option[String],
                                category: Option[RewardingActionCategoryInput],
                                description: Option[String],
                                points: Option[Int]) extends Input[RewardingAction] {

  override def generate: Either[String, RewardingAction] = {
    val missingFields = List(
      retrieveFieldNameIfNotSet(this.name, "name"),
      retrieveFieldNameIfNotSet(this.category, "category"),
      retrieveFieldNameIfNotSet(this.description, "description"),
      retrieveFieldNameIfNotSet(this.points, "points")
    ).flatten

    if (missingFields.isEmpty) {
      val id = UUID.randomUUID()
      val name = this.name.get
      val category = this.category.get
      val description = this.description.get
      val points = this.points.get
      val createdAt = OffsetDateTime.now(Clock.systemUTC())
      val updatedAt = OffsetDateTime.now(Clock.systemUTC())
      category.generate match {
        case Right(retrievedCategory) => Right(RewardingAction(id, name, retrievedCategory, description, points, createdAt, updatedAt))
        case Left(message) => Left("Category in RewardingAction is not a valid category, " + message)
      }
    } else {
      Left(generateMissingFieldsErrorMessage(missingFields))
    }

  }

  override def update(rewardingAction: RewardingAction) = {
    val id = rewardingAction.id
    val name = pick(this.name, rewardingAction.name)
    val category = rewardingAction.category
    val description = pick(this.description, rewardingAction.description)
    val points = pick(this.points, rewardingAction.points)
    val createdAt = rewardingAction.createdAt
    val updatedAt = updateDate(rewardingAction, Seq(name, description, points))
    RewardingAction(id, name.field, category, description.field, points.field, createdAt, updatedAt)
  }

}
