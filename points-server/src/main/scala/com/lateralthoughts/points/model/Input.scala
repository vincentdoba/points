package com.lateralthoughts.points.model

trait Input[T] {

  def retrieveFieldNameIfNotSet[A](field: Option[A], fieldName: String): Option[String] = field match {
    case None => Some(fieldName)
    case Some(_) => None
  }

  def generateMissingFieldsErrorMessage(missingFields: List[String]): String = {
    "The following fields weren't correctly filled in the request : " + missingFields.reduce((a, b) => a + ", " + b)
  }

  def pick[A](candidate: Option[A], current: A) = candidate match {
    case None => current
    case Some(inputCandidate) => inputCandidate
  }

  def generate:Either[String, T]

  def update(base:T):T

}
