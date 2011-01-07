package cqrs.commands;

import java.util.UUID;

public class RemoveItemsFromInventory extends Command {
	public UUID id;
	public final int count;
    public final int originalVersion;

    public RemoveItemsFromInventory(UUID id, int count, int originalVersion) {
		this.id = id;
		this.count = count;
		this.originalVersion = originalVersion;
	}
}
