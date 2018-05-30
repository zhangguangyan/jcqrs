package cqrs.mr.commands

import java.util.UUID

import cqrs.core.Command

class RenameInventoryItem(val id: UUID, val newName: String, val originalVersion: Int) extends Command
