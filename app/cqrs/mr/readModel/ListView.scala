package cqrs.mr.readModel

import cqrs.core.Handler
import cqrs.mr.events.{InventoryItemCreated, InventoryItemDeactivated, InventoryItemRenamed}

import scala.collection.JavaConverters._

object ListView {

  def createInventoryItemCreatedHandler(): Handler[InventoryItemCreated] = {
    (message: InventoryItemCreated) => {
      BullShitDatabase.list.add(new InventoryItemListDto(message.id, message.name))
    }
  }

  def createInventoryItemRenamedHandler(): Handler[InventoryItemRenamed] = {
    (message: InventoryItemRenamed) => {
      val item = BullShitDatabase.list.asScala.find(item => item.id == message.id).get
      item.name = message.newName
    }
  }

  def createInventoryItemDeactivatedHandler(): Handler[InventoryItemDeactivated] = {
    (message: InventoryItemDeactivated) => {
      val items = BullShitDatabase.list.asScala
      val pos = items.indexWhere(item => item.id == message.id)
      items.remove(pos)
    }
  }
}