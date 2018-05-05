package cqrs.mr.infra;

import java.util.List;
import java.util.UUID;

import cqrs.core.AggregateRoot;
import cqrs.core.Event;
import cqrs.core.EventStore;
import cqrs.core.Repository;

public class RepositoryImpl<T extends AggregateRoot> implements Repository<T> {
	private final EventStore eventStore;
	
	public RepositoryImpl(EventStore storage) {
		this.eventStore = storage;
	}

	@Override
	public void save(T aggregate, int expectedVersion) {
		eventStore.saveEvents(aggregate.id(), aggregate.getUncommittedChanges(), expectedVersion);
	}

	@Override
	public T getById(Class<T> type,UUID id) {
		try {
			T obj = type.newInstance(); //call default constructor
			List<Event> history = eventStore.getEventsForAggregate(id);
			obj.loadsFromHistory(history);
			
			return obj;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}