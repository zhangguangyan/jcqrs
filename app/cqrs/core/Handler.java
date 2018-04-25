package cqrs.core;

public interface Handler<T extends Message> {
	void handle(T message);
}
