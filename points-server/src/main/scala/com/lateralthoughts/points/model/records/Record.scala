package com.lateralthoughts.points.model.records

import java.time.OffsetDateTime
import java.util.UUID

/**
  * Represent a record in a database. It has an id, a creation date and an update date
  */
abstract class Record {
  val id:UUID // the id of the record in the database
  val createdAt: OffsetDateTime // the date of creation of the record, ie the first time it was inserted in database
  val updatedAt: OffsetDateTime // the lad update of the record, ie the last time it changed
}
