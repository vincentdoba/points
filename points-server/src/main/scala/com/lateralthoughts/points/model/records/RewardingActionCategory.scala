package com.lateralthoughts.points.model.records

import java.time.OffsetDateTime
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
