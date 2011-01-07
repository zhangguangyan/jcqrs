package cqrs.events;

import java.util.UUID;

public class InventoryItemCreated extends Event {
	public UUID id;
	public String name;

	public InventoryItemCreated(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

}
