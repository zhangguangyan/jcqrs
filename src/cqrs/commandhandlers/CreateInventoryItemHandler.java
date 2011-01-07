package cqrs.commandhandlers;

import cqrs.bus.Handler;
import cqrs.commands.CreateInventoryItem;
import cqrs.domain.InventoryItem;
import cqrs.domain.Repository;

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
