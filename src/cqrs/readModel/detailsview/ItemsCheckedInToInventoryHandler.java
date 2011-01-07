package cqrs.readModel.detailsview;

import cqrs.bus.Handler;
import cqrs.events.ItemsCheckedInToInventory;
import cqrs.readModel.InventoryItemDetailsDto;

public class ItemsCheckedInToInventoryHandler implements
		Handler<ItemsCheckedInToInventory> {

	@Override
	public void handle(ItemsCheckedInToInventory message) {
		InventoryItemDetailsDto d = DetailsView.getDetailsItem(message.id);
		d.currentCount = message.count;
		d.version = message.version;
	}
}
