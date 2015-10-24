package com.lateralthoughts.points.model

import java.time.OffsetDateTime
import java.util.UUID

case class RewardingAction(id:UUID,
                           name:String,
                           category:RewardingActionCategory,
                           description:String,
                           points:Int,
                           createdAt:OffsetDateTime,
                           updatedAt:OffsetDateTime) {

}

case class RewardingActionCategory(id:UUID,
                                   name:String,
                                   description:String,
                                   createdAt:OffsetDateTime,
                                   updatedAt:OffsetDateTime)
