package cqrs.events;

import java.util.UUID;

public class InventoryItemDeactivated extends Event {

	public UUID id;
	public String name;
	public int currentCount;

	public InventoryItemDeactivated(UUID id) {
		this.id = id;
	}
}
