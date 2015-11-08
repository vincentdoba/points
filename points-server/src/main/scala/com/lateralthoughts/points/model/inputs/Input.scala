package com.lateralthoughts.points.model.inputs

import java.time.{Clock, OffsetDateTime}

import com.lateralthoughts.points.model.records.Record
import com.lateralthoughts.points.model.{Changed, Same, Update}

/**
  * An input is the deserialization of the JSON posted to the application. It represents an underlying record
  *
  * @tparam T the type of the record represented by this input
  */
trait Input[T <: Record]

trait SaveInput[T <: Record] extends Input[T]

trait UpdateInput[T <: Record] extends Input[T] {

  private def updated(fields: Seq[Update[Any]]) = fields.map(_.updated).reduce((a, b) => a || b)

  def updateDate(base: T, fields: Seq[Update[Any]]) = updated(fields) match {
    case true => OffsetDateTime.now(Clock.systemUTC())
    case false => base.updatedAt
  }

  def pick[A](candidate: Option[A], current: A): Update[A] = candidate match {
    case None => Same(current)
    case Some(`current`) => Same(current)
    case Some(inputCandidate) => Changed(inputCandidate)
  }
}

trait InnerInput[T <: Record] extends Input[T] {
  def retrieveFieldNameIfNotSet[A](field: Option[A], fieldName: String): Option[String] = field match {
    case None => Some(fieldName)
    case Some(_) => None
  }

  def generateMissingFieldsErrorMessage(missingFields: Seq[String]): String = {
    "The following fields weren't correctly filled in the request : " + missingFields.reduce((a, b) => a + ", " + b)
  }
}


