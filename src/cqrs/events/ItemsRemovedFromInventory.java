package cqrs.events;

import java.util.UUID;

public class ItemsRemovedFromInventory extends Event {
	public UUID id;
	public int count;

	public ItemsRemovedFromInventory(UUID id, int count) {
		this.id = id;
		this.count = count;
	}
}
