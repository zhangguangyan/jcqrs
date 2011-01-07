package cqrs.readModel.detailsview;

import cqrs.bus.Handler;
import cqrs.events.InventoryItemDeactivated;
import cqrs.readModel.BullShitDatabase;

public class InventoryItemDeactivatedHandler implements Handler<InventoryItemDeactivated> {

	@Override
	public void handle(InventoryItemDeactivated message) {
		BullShitDatabase.details.remove(message.id);
	}
}