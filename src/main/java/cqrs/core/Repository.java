package cqrs.core;

import java.util.UUID;

public interface Repository<T extends AggregateRoot> {
	T getById(Class<T> type, UUID id);
	void save(T aggregate, int expectedVersion);
}
