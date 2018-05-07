package cqrs.mr.readModel.detailsview

import cqrs.core.Handler
import cqrs.mr.events.ItemsRemovedFromInventory
import cqrs.mr.readModel.InventoryItemDetailsDto

class ItemsRemovedFromInventoryHandler extends Handler[ItemsRemovedFromInventory] {

	def handle(message: ItemsRemovedFromInventory) {
		val d = DetailsView.getDetailsItem(message.id)
		d.currentCount = message.count
		d.version = message.version
	}
}
