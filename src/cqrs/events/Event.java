package cqrs.events;

import cqrs.bus.Message;

public class Event implements Message {
	public int version;
}
