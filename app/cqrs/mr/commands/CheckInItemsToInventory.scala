package cqrs.mr.commands

import java.util.UUID

import cqrs.core.Command

class CheckInItemsToInventory(val id: UUID, val count: Int, val originalVersion: Int) extends Command
