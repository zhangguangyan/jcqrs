package cqrs.mr.events;

import java.util.UUID;

import cqrs.core.Event;

public class InventoryItemDeactivated extends Event {

	public UUID id;

	public InventoryItemDeactivated(UUID id) {
		this.id = id;
	}
}
