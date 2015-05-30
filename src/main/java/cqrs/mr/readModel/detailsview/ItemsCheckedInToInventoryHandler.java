package cqrs.mr.readModel.detailsview;

import cqrs.core.Handler;
import cqrs.mr.events.ItemsCheckedInToInventory;
import cqrs.mr.readModel.listview.InventoryItemDetailsDto;

public class ItemsCheckedInToInventoryHandler implements
		Handler<ItemsCheckedInToInventory> {

	@Override
	public void handle(ItemsCheckedInToInventory message) {
		InventoryItemDetailsDto d = DetailsView.getDetailsItem(message.id);
		d.currentCount = message.count;
		d.version = message.version;
	}
}
