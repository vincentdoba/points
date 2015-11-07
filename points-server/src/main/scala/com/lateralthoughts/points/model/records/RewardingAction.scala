package com.lateralthoughts.points.model.records

import java.time.OffsetDateTime
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
                           updatedAt: OffsetDateTime) extends Record
