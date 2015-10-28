package com.lateralthoughts.points.repositories

import java.util.UUID

import scala.util.Try

trait Repository[T] {

  def retrieve(id: UUID): Option[T] = None

  def save(document: T): Try[T] = Try {
    document
  }
}
