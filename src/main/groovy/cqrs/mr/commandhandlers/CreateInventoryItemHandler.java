package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.CreateInventoryItem;
import cqrs.mr.domain.InventoryItem;

public class CreateInventoryItemHandler implements Handler<CreateInventoryItem>{
	private final Repository<InventoryItem> repository;
	
	public CreateInventoryItemHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(CreateInventoryItem command) {
		InventoryItem item = new InventoryItem(command.id, command.name);
		repository.save(item, -1);
	}
}
