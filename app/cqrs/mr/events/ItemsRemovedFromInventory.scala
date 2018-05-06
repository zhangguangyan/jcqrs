package cqrs.mr.events

import java.util.UUID

import cqrs.core.Event

class ItemsRemovedFromInventory(val id: UUID, val count: Int) extends Event
