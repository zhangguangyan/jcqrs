package cqrs.mr.commandhandlers

import cqrs.core.Handler
import cqrs.core.Repository
import cqrs.mr.commands.RemoveItemsFromInventory
import cqrs.mr.domain.InventoryItem

class RemoveItemsFromInventoryHandler(repository: Repository[InventoryItem]) extends Handler[RemoveItemsFromInventory] {

  def handle(message: RemoveItemsFromInventory): Unit = {
    val item = repository.getById(classOf[InventoryItem], message.id)
    item.remove(message.count)
    repository.save(item, message.originalVersion)
  }
}
