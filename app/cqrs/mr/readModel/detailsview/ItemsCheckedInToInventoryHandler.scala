package cqrs.mr.readModel.detailsview

import cqrs.core.Handler
import cqrs.mr.events.ItemsCheckedInToInventory
import cqrs.mr.readModel.InventoryItemDetailsDto

class ItemsCheckedInToInventoryHandler extends Handler[ItemsCheckedInToInventory] {

	def handle(message: ItemsCheckedInToInventory) {
		val d = DetailsView.getDetailsItem(message.id)
		d.currentCount = message.count
		d.version = message.version
	}
}
