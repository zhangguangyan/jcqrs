package cqrs.readModel.detailsview;

import java.util.UUID;

import cqrs.bus.Handler;
import cqrs.domain.InvalidOperationException;
import cqrs.events.InventoryItemCreated;
import cqrs.events.InventoryItemDeactivated;
import cqrs.events.InventoryItemRenamed;
import cqrs.events.ItemsCheckedInToInventory;
import cqrs.events.ItemsRemovedFromInventory;
import cqrs.readModel.BullShitDatabase;
import cqrs.readModel.InventoryItemDetailsDto;

public class DetailsView {
	public Handler<InventoryItemCreated> createInventoryItemCreatedHandler() {
		return new InventoryItemCreatedHandler();
	}
	public Handler<InventoryItemDeactivated> createInventoryItemDeactivatedHandler() {
		return new InventoryItemDeactivatedHandler();
	}
	
	public Handler<InventoryItemRenamed> createInventoryItemRenamedHandler() {
		return new InventoryItemRenamedHandler();
	}
	
	public Handler<ItemsCheckedInToInventory> createItemsCheckedInToInventoryHandler() {
		return new ItemsCheckedInToInventoryHandler();
	}
	
	public Handler<ItemsRemovedFromInventory> createItemsRemovedFromInventoryHandler(){
		return new ItemsRemovedFromInventoryHandler();
	}

    public static InventoryItemDetailsDto getDetailsItem(UUID id) {
        InventoryItemDetailsDto d= BullShitDatabase.details.get(id);
        
        if (d==null) {
            throw new InvalidOperationException("did not find the original inventory this shouldnt happen");
        }
        return d;
    }

}
