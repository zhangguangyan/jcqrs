package cqrs.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cqrs.commands.Command;
import cqrs.events.Event;

public class FakeBus  implements CommandSender, EventPublisher
{
    private final HashMap<Class<?>, List<Handler>> routes = new HashMap<Class<?>,List<Handler>>();
    
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

    public <T extends Event> void publish(T event) {
        List<Handler> handlers = routes.get(event.getClass()); 

        if (handlers == null) return;
        
        for(Handler handler : handlers) {
        	handler.handle(event);
        }
    }
}