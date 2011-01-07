package cqrs.commands;

import java.util.UUID;

public class DeactivateInventoryItem extends Command {

	public UUID id;
	public int originalVersion;
	
	public DeactivateInventoryItem(UUID id, int originalVersion) {
		this.id = id;
		this.originalVersion = originalVersion;
	}
}
