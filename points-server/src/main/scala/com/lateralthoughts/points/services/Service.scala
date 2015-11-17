package com.lateralthoughts.points.services

import java.util.UUID

import com.lateralthoughts.points.model.ApplicationError
import com.lateralthoughts.points.repositories.Repository

trait Service[T] {

  val repository:Repository[T]

  def retrieveAll(): Either[ApplicationError, List[T]]

  def retrieve(id: UUID): Either[ApplicationError, T]

}
