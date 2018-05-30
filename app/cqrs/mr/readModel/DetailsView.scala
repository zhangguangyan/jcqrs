package cqrs.mr.readModel

import java.util.UUID

import cqrs.core.Handler
import cqrs.mr.domain.InvalidOperationException
import cqrs.mr.events._

object DetailsView {
  def getDetailsItem(id: UUID): InventoryItemDetailsDto = {
    val d = BullShitDatabase.details.get(id)

    if (d == null) {
      throw new InvalidOperationException("did not find the original inventory this shouldnt happen")
    }
    d
  }

  def createInventoryItemCreatedHandler(): Handler[InventoryItemCreated] = {
    message: InventoryItemCreated => {
      BullShitDatabase.details.put(message.id, new InventoryItemDetailsDto(message.id, message.name, 0, 0))
    }
  }

  def createInventoryItemDeactivatedHandler(): Handler[InventoryItemDeactivated] = {
    message: InventoryItemDeactivated => {
      BullShitDatabase.details.remove(message.id)
    }
  }

  def createInventoryItemRenamedHandler(): Handler[InventoryItemRenamed] = {
    message: InventoryItemRenamed => {
      val d = BullShitDatabase.details.get(message.id)
      d.name = message.newName
      d.version = message.version
    }
  }

  def createItemsCheckedInToInventoryHandler(): Handler[ItemsCheckedInToInventory] = {
    message: ItemsCheckedInToInventory => {
      val d = BullShitDatabase.details.get(message.id)
      d.currentCount = message.count
      d.version = message.version
    }
  }

  def createItemsRemovedFromInventoryHandler(): Handler[ItemsRemovedFromInventory] = {
    message: ItemsRemovedFromInventory => {
      val d = BullShitDatabase.details.get(message.id)
      d.currentCount = message.count
      d.version = message.version
    }
  }
}
