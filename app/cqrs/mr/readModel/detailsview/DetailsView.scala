package cqrs.mr.readModel.detailsview

import java.util.UUID

import cqrs.core.Handler
import cqrs.mr.domain.InvalidOperationException
import cqrs.mr.events.InventoryItemCreated
import cqrs.mr.events.InventoryItemDeactivated
import cqrs.mr.events.InventoryItemRenamed
import cqrs.mr.events.ItemsCheckedInToInventory
import cqrs.mr.events.ItemsRemovedFromInventory
import cqrs.mr.readModel.BullShitDatabase
import cqrs.mr.readModel.InventoryItemDetailsDto

class DetailsView {
	def  createInventoryItemCreatedHandler(): Handler[InventoryItemCreated] = {
		new InventoryItemCreatedHandler()
	}

	def  createInventoryItemDeactivatedHandler(): Handler[InventoryItemDeactivated] = {
		new InventoryItemDeactivatedHandler()
	}
	
	def  createInventoryItemRenamedHandler(): Handler[InventoryItemRenamed] = {
		new InventoryItemRenamedHandler()
	}
	
	def  createItemsCheckedInToInventoryHandler(): Handler[ItemsCheckedInToInventory] = {
		new ItemsCheckedInToInventoryHandler()
	}
	
	def  createItemsRemovedFromInventoryHandler(): Handler[ItemsRemovedFromInventory] = {
		new ItemsRemovedFromInventoryHandler()
	}


}
object DetailsView {
  def getDetailsItem(id: UUID): InventoryItemDetailsDto =  {
    val d= BullShitDatabase.details.get(id)

    if (d==null) {
      throw new InvalidOperationException("did not find the original inventory this shouldnt happen")
    }
    d
  }
}
