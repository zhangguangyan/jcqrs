package cqrs.mr.readModel.detailsview

import cqrs.core.Handler
import cqrs.mr.events.InventoryItemRenamed
import cqrs.mr.readModel.InventoryItemDetailsDto

class InventoryItemRenamedHandler extends Handler[InventoryItemRenamed] {

	def handle(message: InventoryItemRenamed): Unit = {
        val d: InventoryItemDetailsDto = DetailsView.getDetailsItem(message.id)
        d.name = message.newName
        d.version = message.version
	}
}
