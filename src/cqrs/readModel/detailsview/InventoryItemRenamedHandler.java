package cqrs.readModel.detailsview;

import cqrs.bus.Handler;
import cqrs.events.InventoryItemRenamed;
import cqrs.readModel.InventoryItemDetailsDto;

public class InventoryItemRenamedHandler implements
		Handler<InventoryItemRenamed> {

	@Override
	public void handle(InventoryItemRenamed message) {
        InventoryItemDetailsDto d = DetailsView.getDetailsItem(message.id);
        d.name = message.newName;
        d.version = message.version;
	}
}
