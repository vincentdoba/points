package com.lateralthoughts.points.services

import java.util.UUID

import com.lateralthoughts.points.model.{ApplicationError, DatabaseError, RecordNotFound}
import com.lateralthoughts.points.repositories.Repository

import scala.util.{Failure, Success}

trait Service[T] {

  val repository: Repository[T]

  def retrieveAll(): Either[ApplicationError, List[T]] = repository.retrieve() match {
    case Success(elements) => Right(elements)
    case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
  }

  def retrieve(id: UUID): Either[ApplicationError, T] = repository.retrieve(id) match {
    case None => Left(ApplicationError(RecordNotFound, s"No element with id $id found"))
    case Some(rewardingAction) => Right(rewardingAction)
  }

  def delete(id: UUID): Either[ApplicationError, String] = repository.delete(id) match {
    case Success(message) => Right(message)
    case Failure(exception) => Left(ApplicationError(DatabaseError, exception.getMessage))
  }

}
