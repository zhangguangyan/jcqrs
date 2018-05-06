package cqrs.mr.events

import java.util.UUID

import cqrs.core.Event

class InventoryItemCreated(val id: UUID, val name: String) extends Event
