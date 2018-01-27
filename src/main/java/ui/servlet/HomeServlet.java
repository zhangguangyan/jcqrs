package ui.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.mvc.Controller;
import ui.mvc.HomeController;
import cqrs.core.Repository;
import cqrs.core.bus.FakeBus;
import cqrs.core.eventstore.InMemoryEventStore;
import cqrs.mr.commandhandlers.CheckInItemsToInventoryHandler;
import cqrs.mr.commandhandlers.CreateInventoryItemHandler;
import cqrs.mr.commandhandlers.DeactivateInventoryItemHandler;
import cqrs.mr.commandhandlers.RemoveItemsFromInventoryHandler;
import cqrs.mr.commandhandlers.RenameInventoryItemHandler;
import cqrs.mr.commands.CheckInItemsToInventory;
import cqrs.mr.commands.CreateInventoryItem;
import cqrs.mr.commands.DeactivateInventoryItem;
import cqrs.mr.commands.RemoveItemsFromInventory;
import cqrs.mr.commands.RenameInventoryItem;
import cqrs.mr.domain.InventoryItem;
import cqrs.mr.infra.RepositoryImpl;
import cqrs.mr.events.InventoryItemCreated;
import cqrs.mr.events.InventoryItemDeactivated;
import cqrs.mr.events.InventoryItemRenamed;
import cqrs.mr.events.ItemsCheckedInToInventory;
import cqrs.mr.events.ItemsRemovedFromInventory;
import cqrs.mr.readModel.ListView;
import cqrs.mr.readModel.ReadModelFacade;
import cqrs.mr.readModel.detailsview.DetailsView;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RequestProcessor requestProcessor;

	public void init() throws ServletException {
		requestProcessor = new RequestProcessor();
		
		//
        FakeBus bus = new FakeBus();

        InMemoryEventStore storage = new InMemoryEventStore(bus);
        
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