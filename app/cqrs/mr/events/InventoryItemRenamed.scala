package cqrs.mr.events

import java.util.UUID

import cqrs.core.Event

class InventoryItemRenamed(val id: UUID, val newName: String) extends Event
