package cqrs.commandhandlers;

import cqrs.bus.Handler;
import cqrs.commands.RenameInventoryItem;
import cqrs.domain.InventoryItem;
import cqrs.domain.Repository;

public class RenameInventoryItemHandler implements Handler<RenameInventoryItem> {
	private final Repository<InventoryItem> repository;
	
	
	public RenameInventoryItemHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(RenameInventoryItem command) {
		InventoryItem item = repository.getById(InventoryItem.class, command.id);
		item.changeName(command.newName);
		repository.save(item, command.originalVersion);
	}

}
