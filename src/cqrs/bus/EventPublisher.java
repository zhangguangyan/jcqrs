package cqrs.bus;

import cqrs.events.Event;

public interface EventPublisher {
	<T extends Event> void publish(T event);
}