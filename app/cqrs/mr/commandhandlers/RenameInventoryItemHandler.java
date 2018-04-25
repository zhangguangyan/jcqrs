package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.RenameInventoryItem;
import cqrs.mr.domain.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RenameInventoryItemHandler implements Handler<RenameInventoryItem> {
	private final Repository<InventoryItem> repository;

	@Autowired
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
