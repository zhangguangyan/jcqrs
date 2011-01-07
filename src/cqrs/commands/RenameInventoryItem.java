package cqrs.commands;

import java.util.UUID;

public class RenameInventoryItem extends Command {
	public UUID id;
	public String newName;
	public int originalVersion;

	public RenameInventoryItem(UUID id, String newName, int originalVersion) {
		this.id = id;
		this.newName = newName;
		this.originalVersion = originalVersion;
	}
}
