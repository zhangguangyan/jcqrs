package cqrs.mr.readModel;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemCreated;
import cqrs.mr.events.InventoryItemDeactivated;
import cqrs.mr.events.InventoryItemRenamed;

public class ListView {

	public Handler<InventoryItemCreated> createInventoryItemCreatedHandler() {
		return new Handler<InventoryItemCreated>() {
			@Override
			public void handle(InventoryItemCreated message) {
				BullShitDatabase.list.add(new InventoryItemListDto(message.id(), message.name()));
			}
		};
	}

	public Handler<InventoryItemRenamed> createInventoryItemRenamedHandler() {
		return new Handler<InventoryItemRenamed>() {
			@Override
			public void handle(InventoryItemRenamed message) {
				for (InventoryItemListDto item : BullShitDatabase.list) {//simple solution, don't use it in production code!
					if (item.id.equals(message.id())) {
						item.name = message.newName();
						break;
					}
				}
			}
		};
	}

	public Handler<InventoryItemDeactivated> createInventoryItemDeactivatedHandler() {
		return new Handler<InventoryItemDeactivated>() {
			@Override
			public void handle(InventoryItemDeactivated message) {
				for (InventoryItemListDto item : BullShitDatabase.list) {//simple solution, don't use it in production code!
					if (item.id.equals(message.id())) {
						BullShitDatabase.list.remove(item);
						break;
					}
				}
			}
		};
	}
}