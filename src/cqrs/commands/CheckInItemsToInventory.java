package cqrs.commands;

import java.util.UUID;

public class CheckInItemsToInventory extends Command{

	public UUID id;
	public int count;
	public int originalVersion;

	public CheckInItemsToInventory(UUID id, int count, int originalVersion) {
		this.id = id;
		this.count = count;
		this.originalVersion = originalVersion;
	}
}
