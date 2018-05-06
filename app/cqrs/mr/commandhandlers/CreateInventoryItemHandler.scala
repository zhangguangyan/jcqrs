package cqrs.mr.commandhandlers

import cqrs.core.Handler
import cqrs.core.Repository
import cqrs.mr.commands.CreateInventoryItem
import cqrs.mr.domain.InventoryItem

 class CreateInventoryItemHandler(repository: Repository[InventoryItem]) extends Handler[CreateInventoryItem] {
	def handle(command: CreateInventoryItem): Unit = {
		val item = new InventoryItem(command.id, command.name)
		repository.save(item, -1)
	}
}
