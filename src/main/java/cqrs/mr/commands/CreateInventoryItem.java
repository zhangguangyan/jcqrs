package cqrs.mr.commands;

import java.util.UUID;

import cqrs.core.Command;

public class CreateInventoryItem extends Command {

	public UUID id;
	public String name;

	public CreateInventoryItem(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

}
