package cqrs.mr.readModel

import scala.collection.JavaConverters._

import cqrs.core.Handler
import cqrs.mr.events.InventoryItemCreated
import cqrs.mr.events.InventoryItemDeactivated
import cqrs.mr.events.InventoryItemRenamed

class ListView {

  def createInventoryItemCreatedHandler(): Handler[InventoryItemCreated] = {
    (message: InventoryItemCreated) => {
      BullShitDatabase.list.add(new InventoryItemListDto(message.id, message.name))
    }
  }

  def createInventoryItemRenamedHandler(): Handler[InventoryItemRenamed] = {
    (message: InventoryItemRenamed) => {
      BullShitDatabase.list.asScala.filter(item => item.id == message.id)
    }
  }

  def createInventoryItemDeactivatedHandler(): Handler[InventoryItemDeactivated] = {
    (message: InventoryItemDeactivated) => {
      BullShitDatabase.list.asScala.filter(item => item.id != message.id)
    }
  }
}