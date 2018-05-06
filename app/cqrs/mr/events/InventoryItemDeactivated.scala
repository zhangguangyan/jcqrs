package cqrs.mr.events

import java.util.UUID

import cqrs.core.Event

class InventoryItemDeactivated(val id: UUID) extends Event
