package cqrs.mr.readModel.detailsview;

import java.util.UUID;

import cqrs.core.Handler;
import cqrs.mr.domain.InvalidOperationException;
import cqrs.mr.events.InventoryItemCreated;
import cqrs.mr.events.InventoryItemDeactivated;
import cqrs.mr.events.InventoryItemRenamed;
import cqrs.mr.events.ItemsCheckedInToInventory;
import cqrs.mr.events.ItemsRemovedFromInventory;
import cqrs.mr.readModel.BullShitDatabase;
import cqrs.mr.readModel.InventoryItemDetailsDto;

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
        InventoryItemDetailsDto d= BullShitDatabase.details().get(id);
        
        if (d==null) {
            throw new InvalidOperationException("did not find the original inventory this shouldnt happen");
        }
        return d;
    }

}
