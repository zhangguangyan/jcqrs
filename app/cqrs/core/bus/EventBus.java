package cqrs.core.bus;

import cqrs.core.Event;
import cqrs.core.EventPublisher;
import cqrs.core.Handler;
import cqrs.core.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventBus implements EventPublisher {
    private final HashMap<Class<?>, List<Handler>> routes = new HashMap<Class<?>,List<Handler>>();

    public <T extends Message> void registerHandler(Class<T> type, Handler<T> handler) {
        List<Handler> handlers = routes.get(type);

        if (handlers==null) {
            handlers = new ArrayList<Handler>();
            handlers.add(handler);
            routes.put(type, handlers);
        } else {
            handlers.add(handler);
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
