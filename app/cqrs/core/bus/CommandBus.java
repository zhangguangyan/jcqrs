package cqrs.core.bus;

import cqrs.core.Command;
import cqrs.core.CommandSender;
import cqrs.core.Handler;
import cqrs.core.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandBus implements CommandSender {
    private final HashMap<Class<?>, List<Handler>> routes = new HashMap<Class<?>,List<Handler>>();

    public CommandBus() {}

    public <T extends Message> void registerHandler(Class<T> type,Handler<T> handler) {
        List<Handler> handlers = routes.get(type);
   
        if (handlers==null) {
            handlers = new ArrayList<Handler>();
            handlers.add(handler);
            routes.put(type, handlers);
        } else {
        	handlers.add(handler);
        }
    }

    public <T extends Command> void send(T command) {
        List<Handler> handlers = routes.get(command.getClass()); 
        if (handlers!=null) {
            if (handlers.size() != 1) throw new RuntimeException("cannot send to more than one handler");
            Handler<T> handler = handlers.get(0);
            handler.handle(command);
        } else {
            throw new RuntimeException("no handler registered");
        }
    }
}