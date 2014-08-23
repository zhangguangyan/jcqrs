package cqrs.mr.events;

import java.util.UUID;

import cqrs.core.Event;

public class ItemsCheckedInToInventory extends Event {

	public UUID id;
	public int count;

	public ItemsCheckedInToInventory(UUID id, int count) {
		this.id = id;
		this.count = count;
	}
}
