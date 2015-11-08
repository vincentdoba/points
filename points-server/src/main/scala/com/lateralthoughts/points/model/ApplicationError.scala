package com.lateralthoughts.points.model

/**
  * Class used to represent an error in application
  *
  * @param code the internal error code (JsonNotValid...)
  * @param message the message describing the error
  */
case class ApplicationError(code:ErrorCode, message: String)

sealed trait ErrorCode { def name:String}

case object JsonNotValid extends ErrorCode { val name = "JsonNotValid"}
case object UUIDNotValid extends ErrorCode { val name = "UUIDNotValid"}
case object InputObjectIncomplete extends ErrorCode { val name = "InputObjectIncomplete"}
case object InputObjectNotValid extends ErrorCode { val name = "InputObjectNotValid"}
case object RecordNotFound extends ErrorCode { val name = "RecordNotFound"}
case object DatabaseError extends ErrorCode { val name = "DatabaseError"}
case object NotCoded extends ErrorCode { val name = "NotCoded"}

case class UnknownError(name:String) extends ErrorCode

object ErrorCode {
  def parse(name:String) = name match {
    case "JsonNotValid" => JsonNotValid
    case "UUIDNotValid" => UUIDNotValid
    case "InputObjectIncomplete" => InputObjectIncomplete
    case "InputObjectNotValid" => InputObjectNotValid
    case "RecordNotFound" => RecordNotFound
    case "DatabaseError" => DatabaseError
    case "NotCoded" => NotCoded
    case unknownError => UnknownError(unknownError)
  }
}
