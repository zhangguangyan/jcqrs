package cqrs.mr.readModel.detailsview;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemDeactivated;
import cqrs.mr.readModel.BullShitDatabase;

public class InventoryItemDeactivatedHandler implements Handler<InventoryItemDeactivated> {

	@Override
	public void handle(InventoryItemDeactivated message) {
		BullShitDatabase.details().remove(message.id());
	}
}