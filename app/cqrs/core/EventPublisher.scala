package cqrs.core

trait EventPublisher {
  def publish[T <: Event](event: T)
}