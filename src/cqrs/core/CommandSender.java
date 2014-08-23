package cqrs.core;


public interface CommandSender {
	<T extends Command> void send(T command);
}

