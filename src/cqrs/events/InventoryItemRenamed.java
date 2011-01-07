package cqrs.events;

import java.util.UUID;

public class InventoryItemRenamed extends Event {

	public UUID id;
	public String newName;
	public int currentCount;

	public InventoryItemRenamed(UUID id, String newName) {
		this.id = id;
		this.newName = newName;
	}
}
