package cqrs.mr.commandhandlers

import cqrs.core.Handler
import cqrs.core.Repository
import cqrs.mr.commands.CheckInItemsToInventory
import cqrs.mr.domain.InventoryItem

class CheckInItemsToInventoryHandler(repository: Repository[InventoryItem]) extends Handler[CheckInItemsToInventory] {
	def handle(message: CheckInItemsToInventory): Unit = {
		val item = repository.getById(classOf[InventoryItem], message.id)
		item.checkIn(message.count)
		repository.save(item,message.originalVersion)
	}
}
