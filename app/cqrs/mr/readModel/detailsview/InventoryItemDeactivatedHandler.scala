package cqrs.mr.readModel.detailsview

import cqrs.core.Handler
import cqrs.mr.events.InventoryItemDeactivated
import cqrs.mr.readModel.BullShitDatabase

class InventoryItemDeactivatedHandler extends Handler[InventoryItemDeactivated] {

	def handle(message: InventoryItemDeactivated) {
		BullShitDatabase.details.remove(message.id)
	}
}