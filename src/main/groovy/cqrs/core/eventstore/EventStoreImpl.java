package cqrs.core.eventstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cqrs.core.Event;
import cqrs.core.EventPublisher;
import cqrs.core.EventStore;

public class EventStoreImpl implements EventStore {
	private final EventPublisher publisher;
	private final HashMap<UUID, List<EventDescriptor>> current = new HashMap<UUID, List<EventDescriptor>>(); 

	public EventStoreImpl(EventPublisher publisher){
		this.publisher = publisher;
	}
	
	@Override
    public void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion) {
        List<EventDescriptor> eventDescriptors = current.get(aggregateId);
        if (eventDescriptors == null) {
            eventDescriptors = new ArrayList<EventDescriptor>();
            current.put(aggregateId,eventDescriptors);
        } else if (eventDescriptors.get(eventDescriptors.size() - 1).version != expectedVersion && expectedVersion != -1) {
            throw new ConcurrencyException();
        }
        //add events
        int i = expectedVersion;
        for (Event event : events) {
            i++;
            event.version = i;
            eventDescriptors.add(new EventDescriptor(aggregateId,event,i));
            publisher.publish(event);
        }
    }

    public  List<Event> getEventsForAggregate(UUID aggregateId) {
        List<EventDescriptor> eventDescriptors = current.get(aggregateId);;
        if (eventDescriptors==null){
            throw new RuntimeException("AggregateNotFoundException");
        }
        //return eventDescriptors.Select(desc => desc.EventData).ToList();
        List<Event> events = new ArrayList<Event>();
        for (EventDescriptor desc:eventDescriptors) {
        	events.add(desc.eventData);
        }
        return events;
    }

    static class EventDescriptor {
        
    	public final UUID id;
        public final Event eventData;
        public final int version;

        public EventDescriptor(UUID id, Event eventData, int version) {
        	this.id = id;
            this.eventData = eventData;
            this.version = version;
        }
    }
}