package cqrs.readModel.detailsview;

import cqrs.bus.Handler;
import cqrs.events.InventoryItemCreated;
import cqrs.readModel.BullShitDatabase;
import cqrs.readModel.InventoryItemDetailsDto;

public class InventoryItemCreatedHandler implements Handler<InventoryItemCreated>{
	@Override
	public void handle(InventoryItemCreated message) {
		BullShitDatabase.details.put(message.id, new InventoryItemDetailsDto(message.id, message.name, 0,0));
	}
}
