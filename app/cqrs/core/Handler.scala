package cqrs.core

trait Handler[T <: Message] {
  def handle(message: T): Unit
}
