package cqrs.core.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cqrs.core.*;
import cqrs.mr.commandhandlers.*;
import cqrs.mr.commands.*;
import cqrs.mr.domain.InventoryItem;
import cqrs.mr.events.*;
import cqrs.mr.infra.RepositoryImpl;
import cqrs.mr.readModel.detailsview.DetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandBus implements CommandSender {
    private final HashMap<Class<?>, List<Handler>> routes = new HashMap<Class<?>,List<Handler>>();

    public CommandBus() {}

    @Autowired
    public CommandBus(ApplicationContext ctx) {
        registerHandler(CreateInventoryItem.class, ctx.getBean(CreateInventoryItemHandler.class));
        registerHandler(CheckInItemsToInventory.class, ctx.getBean(CheckInItemsToInventoryHandler.class));
        registerHandler(DeactivateInventoryItem.class, ctx.getBean(DeactivateInventoryItemHandler.class));
        registerHandler(RemoveItemsFromInventory.class, ctx.getBean(RemoveItemsFromInventoryHandler.class));
        registerHandler(RenameInventoryItem.class, ctx.getBean(RenameInventoryItemHandler.class));
    }
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