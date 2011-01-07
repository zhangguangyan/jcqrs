package cqrs.events;

import java.util.UUID;

public class ItemsCheckedInToInventory extends Event {

	public UUID id;
	public int count;

	public ItemsCheckedInToInventory(UUID id, int count) {
		this.id = id;
		this.count = count;
	}
}
