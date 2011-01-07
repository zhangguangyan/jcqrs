package cqrs.eventstore;

import java.util.List;
import java.util.UUID;

import cqrs.events.Event;

public interface EventStore {
    void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion);
    List<Event> getEventsForAggregate(UUID aggregateId);

}
