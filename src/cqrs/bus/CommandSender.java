package cqrs.bus;

import cqrs.commands.Command;

public interface CommandSender
{
	<T extends Command> void send(T command);

}

