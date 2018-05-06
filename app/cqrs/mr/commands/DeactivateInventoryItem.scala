package cqrs.mr.commands

import java.util.UUID

import cqrs.core.Command

class DeactivateInventoryItem(val id: UUID , val originalVersion: Int) extends Command
