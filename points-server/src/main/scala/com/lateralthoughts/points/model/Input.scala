package com.lateralthoughts.points.model

import java.time.{Clock, OffsetDateTime}

/**
  * An input is the deserialization of the JSON posted to the application. It represents an underlying record
  *
  * @tparam T the type of the record represented by this input
  */
trait Input[T<:Record] {

  def retrieveFieldNameIfNotSet[A](field: Option[A], fieldName: String): Option[String] = field match {
    case None => Some(fieldName)
    case Some(_) => None
  }

  def generateMissingFieldsErrorMessage(missingFields: List[String]): String = {
    "The following fields weren't correctly filled in the request : " + missingFields.reduce((a, b) => a + ", " + b)
  }

  def pick[A](candidate: Option[A], current: A):Update[A] = candidate match {
    case None => Same(current)
    case Some(`current`) => Same(current)
    case Some(inputCandidate) => Changed(inputCandidate)
  }

  def generate:Either[String, T]

  def update(base:T):T

  private def updated(fields : Seq[Update[Any]]) = fields.map(_.updated).reduce((a,b) => a || b)

  def updateDate(base:T, fields : Seq[Update[Any]]) = updated(fields) match {
    case true => OffsetDateTime.now(Clock.systemUTC())
    case false => base.updatedAt
  }

}

/**
  * Trait used to determine if a field has been updated or not
  *
  * @tparam A the type of the updated (or not) field
  */
sealed trait Update[+A] {
  def field:A
  def updated:Boolean
}

case class Changed[A](field: A) extends Update[A] {
  def updated = true
}
case class Same[A](field: A) extends Update[A] {
  def updated = false
}
