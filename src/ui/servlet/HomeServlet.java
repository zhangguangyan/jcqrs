package ui.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.mvc.Controller;
import ui.mvc.HomeController;
import cqrs.bus.FakeBus;
import cqrs.commandhandlers.CheckInItemsToInventoryHandler;
import cqrs.commandhandlers.CreateInventoryItemHandler;
import cqrs.commandhandlers.DeactivateInventoryItemHandler;
import cqrs.commandhandlers.RemoveItemsFromInventoryHandler;
import cqrs.commandhandlers.RenameInventoryItemHandler;
import cqrs.commands.CheckInItemsToInventory;
import cqrs.commands.CreateInventoryItem;
import cqrs.commands.DeactivateInventoryItem;
import cqrs.commands.RemoveItemsFromInventory;
import cqrs.commands.RenameInventoryItem;
import cqrs.domain.InventoryItem;
import cqrs.domain.Repository;
import cqrs.domain.RepositoryImpl;
import cqrs.events.InventoryItemCreated;
import cqrs.events.InventoryItemDeactivated;
import cqrs.events.InventoryItemRenamed;
import cqrs.events.ItemsCheckedInToInventory;
import cqrs.events.ItemsRemovedFromInventory;
import cqrs.eventstore.EventStoreImpl;
import cqrs.readModel.ListView;
import cqrs.readModel.ReadModelFacade;
import cqrs.readModel.detailsview.DetailsView;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestProcessor requestProcessor;

	public void init() throws ServletException {
		requestProcessor = new RequestProcessor();
		
		//
        FakeBus bus = new FakeBus();

        EventStoreImpl storage = new EventStoreImpl(bus);
        
        //--command handlers
        Repository<InventoryItem> repo = new RepositoryImpl<InventoryItem>(storage);
        bus.registerHandler(CreateInventoryItem.class,new CreateInventoryItemHandler(repo));
        bus.registerHandler(CheckInItemsToInventory.class,new CheckInItemsToInventoryHandler(repo));
        bus.registerHandler(DeactivateInventoryItem.class,new DeactivateInventoryItemHandler(repo));
        bus.registerHandler(RemoveItemsFromInventory.class,new RemoveItemsFromInventoryHandler(repo));
        bus.registerHandler(RenameInventoryItem.class,new RenameInventoryItemHandler(repo));
        
        //--event handlers: details view
        DetailsView detailsView = new DetailsView();
        bus.registerHandler(InventoryItemCreated.class,detailsView.createInventoryItemCreatedHandler());
        bus.registerHandler(InventoryItemDeactivated.class, detailsView.createInventoryItemDeactivatedHandler());
        bus.registerHandler(InventoryItemRenamed.class, detailsView.createInventoryItemRenamedHandler());
        bus.registerHandler(ItemsCheckedInToInventory.class, detailsView.createItemsCheckedInToInventoryHandler());
        bus.registerHandler(ItemsRemovedFromInventory.class, detailsView.createItemsRemovedFromInventoryHandler());

        //--event handlers: list view
        ListView listView = new ListView();
        bus.registerHandler(InventoryItemCreated.class, listView.createInventoryItemCreatedHandler());
        bus.registerHandler(InventoryItemRenamed.class, listView.createInventoryItemRenamedHandler());
        bus.registerHandler(InventoryItemDeactivated.class, listView.createInventoryItemDeactivatedHandler());


   		ReadModelFacade readmodel = new ReadModelFacade();
        Controller controller = new HomeController(bus,readmodel);
        requestProcessor.setController(controller);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doProcess(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		requestProcessor.process(request,response);
	}
}