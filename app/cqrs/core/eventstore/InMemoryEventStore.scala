package cqrs.core.eventstore

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.UUID

import scala.collection.JavaConverters._
import cqrs.core.Event
import cqrs.core.EventPublisher
import cqrs.core.EventStore

class InMemoryEventStore(publisher: EventPublisher) extends EventStore {
  private val current = new HashMap[UUID, List[EventDescriptor]]()

  def saveEvents(aggregateId: UUID, events: List[Event], expectedVersion: Int): Unit = {
    var eventDescriptors = current.get(aggregateId)
    if (eventDescriptors == null) {
      eventDescriptors = new ArrayList[EventDescriptor]()
      current.put(aggregateId, eventDescriptors)
    } else if (eventDescriptors.get(eventDescriptors.size() - 1).version != expectedVersion && expectedVersion != -1) {
      throw new ConcurrencyException()
    }
    //add events
    var i = expectedVersion
    events.forEach { event =>
      //for (event <- events){
      i += 1
      event.version = i
      eventDescriptors.add(new EventDescriptor(aggregateId, event, i))
      publisher.publish(event)
    }
  }

  def getEventsForAggregate(aggregateId: UUID): List[Event] = {
    val eventDescriptors = current.get(aggregateId).asScala
    if (eventDescriptors == null) {
      throw new RuntimeException("AggregateNotFoundException")
    }
    //return eventDescriptors.Select(desc => desc.EventData).ToList()
    eventDescriptors.map(desc => desc.eventData).asJava
  }
}

class EventDescriptor(val id: UUID, val eventData: Event, val version: Int)
