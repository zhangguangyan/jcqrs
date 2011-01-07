package cqrs.commandhandlers;

import cqrs.bus.Handler;
import cqrs.commands.DeactivateInventoryItem;
import cqrs.domain.InventoryItem;
import cqrs.domain.Repository;

public class DeactivateInventoryItemHandler implements Handler<DeactivateInventoryItem> {
	private final Repository<InventoryItem> repository;
	
	public DeactivateInventoryItemHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(DeactivateInventoryItem command) {
		InventoryItem item = repository.getById(InventoryItem.class,command.id);
		item.deactivate();
		repository.save(item, command.originalVersion);
	}
}
