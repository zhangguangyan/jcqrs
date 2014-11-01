package cqrs.mr.readModel.mongo;

import java.util.UUID;

import com.mongodb.MongoClient;

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
	public Handler<InventoryItemCreated> createInventoryItemCreatedHandler(MongoClient mongo) {
		return new InventoryItemCreatedHandler(mongo);
	}
	public Handler<InventoryItemDeactivated> createInventoryItemDeactivatedHandler(MongoClient mongo) {
		return new InventoryItemDeactivatedHandler(mongo);
	}
	
	public Handler<InventoryItemRenamed> createInventoryItemRenamedHandler(MongoClient mongo) {
		return new InventoryItemRenamedHandler(mongo);
	}
	
	public Handler<ItemsCheckedInToInventory> createItemsCheckedInToInventoryHandler(MongoClient mongo) {
		return new ItemsCheckedInToInventoryHandler(mongo);
	}
	
	public Handler<ItemsRemovedFromInventory> createItemsRemovedFromInventoryHandler(MongoClient mongo){
		return new ItemsRemovedFromInventoryHandler(mongo);
	}

    public static InventoryItemDetailsDto getDetailsItem(UUID id) {
        InventoryItemDetailsDto d= BullShitDatabase.details.get(id);
        
        if (d==null) {
            throw new InvalidOperationException("did not find the original inventory this shouldnt happen");
        }
        return d;
    }

}
