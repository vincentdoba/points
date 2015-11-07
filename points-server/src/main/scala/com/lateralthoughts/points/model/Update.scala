package com.lateralthoughts.points.model

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
