package com.lateralthoughts.points.model

/**
  * Class used to represent an error in application
  *
  * @param code the internal error code (JsonNotValid...)
  * @param message the message describing the error
  */
case class ApplicationError(code:ErrorCode, message: String)

sealed trait ErrorCode

case object JsonNotValid extends ErrorCode
case object DatabaseError extends ErrorCode
case object UUIDNotValid extends ErrorCode
case object InputObjectIncomplete extends ErrorCode
case object InputObjectNotValid extends ErrorCode
case object NotCoded extends ErrorCode
