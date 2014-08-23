package cqrs.core;

import java.util.UUID;

public interface Repository<T extends AggregateRoot> {
	public T getById(Class<T> type, UUID id);
	public void save(T aggregate, int expectedVersion);

}
