package cqrs.mr.readModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cqrs.mr.readModel.listview.InventoryItemDetailsDto;
import cqrs.mr.readModel.listview.InventoryItemListDto;

public class ReadModelFacade {

	public List<InventoryItemListDto> getInventoryItems() {
//		List<InventoryItemListDto> items = new ArrayList<InventoryItemListDto>();
//		items.add(new InventoryItemListDto(UUID.randomUUID(),"name1111"));
//		return items;
		return BullShitDatabase.list;
	}

	public InventoryItemDetailsDto getInventoryItemDetails(UUID id) {
		//return new InventoryItemDetailsDto(id,"name",10,1);
		return BullShitDatabase.details.get(id);
	}
}
