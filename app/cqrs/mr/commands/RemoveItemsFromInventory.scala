package cqrs.mr.commands

import java.util.UUID

import cqrs.core.Command

class RemoveItemsFromInventory(val id: UUID, val count: Int, val originalVersion: Int) extends Command
