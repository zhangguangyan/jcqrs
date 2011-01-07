package cqrs.readModel.detailsview;

import cqrs.bus.Handler;
import cqrs.events.ItemsRemovedFromInventory;
import cqrs.readModel.InventoryItemDetailsDto;

public class ItemsRemovedFromInventoryHandler implements
		Handler<ItemsRemovedFromInventory> {

	@Override
	public void handle(ItemsRemovedFromInventory message) {
		InventoryItemDetailsDto d = DetailsView.getDetailsItem(message.id);
		d.currentCount = message.count;
		d.version = message.version;
	}
}
