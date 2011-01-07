package cqrs.commandhandlers;

import cqrs.bus.Handler;
import cqrs.commands.RemoveItemsFromInventory;
import cqrs.domain.InventoryItem;
import cqrs.domain.Repository;

public class RemoveItemsFromInventoryHandler implements Handler<RemoveItemsFromInventory>{
	private final Repository<InventoryItem> repository;
	
	public RemoveItemsFromInventoryHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(RemoveItemsFromInventory message) {
		InventoryItem item = repository.getById(InventoryItem.class, message.id);
		item.remove(message.count);
		repository.save(item, message.originalVersion);
	}

}
