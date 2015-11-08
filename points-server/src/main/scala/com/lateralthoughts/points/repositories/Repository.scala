package com.lateralthoughts.points.repositories

import java.util.UUID

import scala.util.Try

trait Repository[T] {

  def retrieve(id: UUID): Option[T] = None

  def retrieve():List[T] = List()

  def save(document: T): Try[T] = Try {
    document
  }

  def delete(id: UUID): Try[String] = Try {
    "Successfully deleted"
  }
}
