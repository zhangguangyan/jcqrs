package cqrs.mr.readModel

import java.util.{ArrayList, HashMap, UUID}

object BullShitDatabase {
  val details = new HashMap[UUID, InventoryItemDetailsDto]()
  val list = new ArrayList[InventoryItemListDto]()
}
