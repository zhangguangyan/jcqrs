package cqrs.bus;


public interface Handler<T extends Message> {
	public void handle(T message);
}
