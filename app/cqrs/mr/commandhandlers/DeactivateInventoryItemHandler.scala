package cqrs.mr.commandhandlers

import cqrs.core.Handler
import cqrs.core.Repository
import cqrs.mr.commands.DeactivateInventoryItem
import cqrs.mr.domain.InventoryItem

class DeactivateInventoryItemHandler(repository: Repository[InventoryItem]) extends Handler[DeactivateInventoryItem] {

  def handle(command: DeactivateInventoryItem): Unit = {
    val item = repository.getById(classOf[InventoryItem], command.id)
    item.deactivate()
    repository.save(item, command.originalVersion)
  }
}
