package cqrs.commandhandlers;

import cqrs.bus.Handler;
import cqrs.commands.CheckInItemsToInventory;
import cqrs.domain.InventoryItem;
import cqrs.domain.Repository;

public class CheckInItemsToInventoryHandler implements Handler<CheckInItemsToInventory> {
	private final Repository<InventoryItem> repository;

	public CheckInItemsToInventoryHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}
	
	@Override
	public void handle(CheckInItemsToInventory message) {
		InventoryItem item = repository.getById(InventoryItem.class, message.id);
		item.checkIn(message.count);
		repository.save(item,message.originalVersion);
	}
}
