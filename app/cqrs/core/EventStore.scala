package cqrs.core

import java.util.List
import java.util.UUID

trait EventStore {
  def saveEvents(aggregateId: UUID, events: List[Event], expectedVersion: Int): Unit
  def getEventsForAggregate(aggregateId: UUID): List[Event]
}
