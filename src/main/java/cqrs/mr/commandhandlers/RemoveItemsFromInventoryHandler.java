package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.RemoveItemsFromInventory;
import cqrs.mr.domain.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveItemsFromInventoryHandler implements Handler<RemoveItemsFromInventory>{
	private final Repository<InventoryItem> repository;

	@Autowired
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
