package cqrs.mr.events;

import java.util.UUID;

import cqrs.core.Event;

public class InventoryItemRenamed extends Event {

	public UUID id;
	public String newName;
	public int currentCount;

	public InventoryItemRenamed(UUID id, String newName) {
		this.id = id;
		this.newName = newName;
	}
}
