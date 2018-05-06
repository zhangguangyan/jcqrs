package cqrs.mr.readModel.detailsview;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemRenamed;
import cqrs.mr.readModel.InventoryItemDetailsDto;

public class InventoryItemRenamedHandler implements
		Handler<InventoryItemRenamed> {

	@Override
	public void handle(InventoryItemRenamed message) {
        InventoryItemDetailsDto d = DetailsView.getDetailsItem(message.id());
        d.name = message.newName();
        d.version = message.version();
	}
}
