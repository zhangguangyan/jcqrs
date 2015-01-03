package cqrs.mr.readModel.detailsview;

import cqrs.core.Handler;
import cqrs.mr.events.ItemsRemovedFromInventory;
import cqrs.mr.readModel.InventoryItemDetailsDto;

public class ItemsRemovedFromInventoryHandler implements
		Handler<ItemsRemovedFromInventory> {

	@Override
	public void handle(ItemsRemovedFromInventory message) {
		InventoryItemDetailsDto d = DetailsView.getDetailsItem(message.id);
		d.currentCount = message.count;
		d.version = message.version;
	}
}
