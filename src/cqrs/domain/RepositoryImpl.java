package cqrs.domain;

import java.util.List;
import java.util.UUID;

import cqrs.events.Event;
import cqrs.eventstore.EventStore;

public class RepositoryImpl<T extends AggregateRoot> implements Repository<T> {
	private final EventStore storage;
	
	public RepositoryImpl(EventStore storage) {
		this.storage = storage;
	}

	@Override
	public void save(T aggregate, int expectedVersion) {
		storage.saveEvents(aggregate.id, aggregate.getUncommittedChanges(), expectedVersion);
	}

	@Override
	public T getById(Class<T> type,UUID id) {
		try {
			T obj = type.newInstance();
			List<Event> history = storage.getEventsForAggregate(id);
			obj.loadsFromHistory(history);
			
			return obj;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}