package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.CreateInventoryItem;
import cqrs.mr.domain.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateInventoryItemHandler implements Handler<CreateInventoryItem>{
	private final Repository<InventoryItem> repository;

	@Autowired
	public CreateInventoryItemHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(CreateInventoryItem command) {
		InventoryItem item = new InventoryItem(command.id, command.name);
		repository.save(item, -1);
	}
}
