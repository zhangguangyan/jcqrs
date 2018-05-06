package cqrs.mr.commandhandlers

import cqrs.core.Handler
import cqrs.core.Repository
import cqrs.mr.commands.RenameInventoryItem
import cqrs.mr.domain.InventoryItem

class RenameInventoryItemHandler(repository: Repository[InventoryItem]) extends Handler[RenameInventoryItem] {

	def handle(command: RenameInventoryItem): Unit = {
		val item = repository.getById(classOf[InventoryItem], command.id)
		item.changeName(command.newName)
		repository.save(item, command.originalVersion)
	}
}
