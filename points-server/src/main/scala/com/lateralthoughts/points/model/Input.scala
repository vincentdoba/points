package com.lateralthoughts.points.model

trait Input {

  def retrieveFieldNameIfNotSet[T](field: Option[T], fieldName: String): Option[String] = {
    field match {
      case None => Some(fieldName)
      case Some(_) => None
    }
  }

  def generateMissingFieldsErrorMessage(missingFields: List[String]): String = {
    "The following fields weren't correctly filled in the request : " + missingFields.reduce((a, b) => a + ", " + b)
  }

  def pick[T](candidate: Option[T], current: T) = candidate match {
    case None => current
    case Some(inputCandidate) => inputCandidate
  }

}
