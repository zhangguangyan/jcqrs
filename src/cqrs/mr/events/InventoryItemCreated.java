package cqrs.mr.events;

import java.util.UUID;

import cqrs.core.Event;

public class InventoryItemCreated extends Event {
	public UUID id;
	public String name;

	public InventoryItemCreated(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

}
