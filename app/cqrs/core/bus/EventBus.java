package cqrs.core.bus;

import cqrs.core.*;
import cqrs.mr.commandhandlers.*;
import cqrs.mr.commands.*;
import cqrs.mr.events.*;
import cqrs.mr.readModel.ListView;
import cqrs.mr.readModel.detailsview.DetailsView;

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
