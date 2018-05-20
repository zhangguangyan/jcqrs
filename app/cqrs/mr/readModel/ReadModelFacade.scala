package cqrs.mr.readModel

import java.util.{List, UUID}

class ReadModelFacade {

	def  getInventoryItems():List[InventoryItemListDto] = {
//		List<InventoryItemListDto> items = new ArrayList<InventoryItemListDto>()
//		items.add(new InventoryItemListDto(UUID.randomUUID(),"name1111"))
//		return items
		BullShitDatabase.list
	}

	def getInventoryItemDetails(id: UUID): InventoryItemDetailsDto = {
		//return new InventoryItemDetailsDto(id,"name",10,1)
		BullShitDatabase.details.get(id)
	}
}
