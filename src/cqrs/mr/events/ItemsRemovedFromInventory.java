package cqrs.mr.events;

import java.util.UUID;

import cqrs.core.Event;

public class ItemsRemovedFromInventory extends Event {
	public UUID id;
	public int count;

	public ItemsRemovedFromInventory(UUID id, int count) {
		this.id = id;
		this.count = count;
	}
}
