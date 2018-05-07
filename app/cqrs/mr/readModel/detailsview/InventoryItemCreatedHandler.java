package cqrs.mr.readModel.detailsview;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemCreated;
import cqrs.mr.readModel.BullShitDatabase;
import cqrs.mr.readModel.InventoryItemDetailsDto;

public class InventoryItemCreatedHandler implements Handler<InventoryItemCreated>{
	@Override
	public void handle(InventoryItemCreated message) {
		BullShitDatabase.details().put(message.id(), new InventoryItemDetailsDto(message.id(), message.name(), 0,0));
	}
}
