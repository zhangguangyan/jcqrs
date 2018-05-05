package cqrs.core

import java.util.UUID

trait Repository[T <: AggregateRoot] {
	def getById(clazz: Class[T], id: UUID ): T
	def save(aggregate: T, expectedVersion: Int): Unit
}
