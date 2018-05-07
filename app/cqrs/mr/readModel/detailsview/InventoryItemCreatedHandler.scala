package cqrs.mr.readModel.detailsview

import cqrs.core.Handler
import cqrs.mr.events.InventoryItemCreated
import cqrs.mr.readModel.BullShitDatabase
import cqrs.mr.readModel.InventoryItemDetailsDto

class InventoryItemCreatedHandler extends Handler[InventoryItemCreated]{

	def handle(message: InventoryItemCreated) {
		BullShitDatabase.details.put(message.id, new InventoryItemDetailsDto(message.id, message.name,0,0))
	}
}
