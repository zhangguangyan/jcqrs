package cqrs.mr.commands

import java.util.UUID

import cqrs.core.Command

class CreateInventoryItem(val id: UUID, val name: String) extends Command
