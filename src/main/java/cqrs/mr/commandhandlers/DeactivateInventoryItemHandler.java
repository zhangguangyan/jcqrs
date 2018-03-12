package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.DeactivateInventoryItem;
import cqrs.mr.domain.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeactivateInventoryItemHandler implements Handler<DeactivateInventoryItem> {
	private final Repository<InventoryItem> repository;

	@Autowired
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
