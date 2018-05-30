package cqrs.mr.infra

import java.util.UUID

import cqrs.core.{AggregateRoot, EventStore, Repository}

class RepositoryImpl[T <: AggregateRoot](eventStore: EventStore) extends Repository[T] {

  def save(aggregate: T, expectedVersion: Int): Unit = {
    eventStore.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), expectedVersion)
  }

  def getById(clazz: Class[T], id: UUID): T = {
    try {
      val obj: T = clazz.newInstance() //call default constructor
      val history = eventStore.getEventsForAggregate(id)
      obj.loadsFromHistory(history)
      obj
    } catch {
      case e: InstantiationException =>
        throw new RuntimeException(e)
      case e: IllegalAccessException =>
        throw new RuntimeException(e)
    }
  }
}

