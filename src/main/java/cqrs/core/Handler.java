package cqrs.core;

public interface Handler<T extends Message> {
	public void handle(T message);
}
