package cqrs.core;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion);
    List<Event> getEventsForAggregate(UUID aggregateId);

}
