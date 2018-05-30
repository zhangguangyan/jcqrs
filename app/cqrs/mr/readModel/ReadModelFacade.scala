package cqrs.mr.readModel

import java.util.{List, UUID}

class ReadModelFacade {

  def getInventoryItems(): List[InventoryItemListDto] = {
    BullShitDatabase.list
  }

  def getInventoryItemDetails(id: UUID): InventoryItemDetailsDto = {
    BullShitDatabase.details.get(id)
  }
}
