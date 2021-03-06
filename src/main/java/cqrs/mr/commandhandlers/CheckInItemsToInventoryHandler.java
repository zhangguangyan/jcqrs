package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.CheckInItemsToInventory;
import cqrs.mr.domain.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckInItemsToInventoryHandler implements Handler<CheckInItemsToInventory> {
	private final Repository<InventoryItem> repository;

	@Autowired
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
