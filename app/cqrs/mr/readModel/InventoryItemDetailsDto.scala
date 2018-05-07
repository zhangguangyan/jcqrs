package cqrs.mr.readModel

import java.util.UUID

class InventoryItemDetailsDto(var id: UUID, var name: String, var currentCount: Int, var version: Int)
