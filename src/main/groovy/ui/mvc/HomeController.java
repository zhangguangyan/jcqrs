package ui.mvc;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cqrs.core.bus.FakeBus;
import cqrs.mr.commands.CheckInItemsToInventory;
import cqrs.mr.commands.CreateInventoryItem;
import cqrs.mr.commands.DeactivateInventoryItem;
import cqrs.mr.commands.RemoveItemsFromInventory;
import cqrs.mr.commands.RenameInventoryItem;
import cqrs.mr.readModel.ReadModelFacade;

public class HomeController implements Controller {
	private FakeBus bus;
	private ReadModelFacade readmodel;

	public HomeController(FakeBus bus, ReadModelFacade readModel) {
		this.bus = bus;
		this.readmodel = readModel;
	}

	public View index() throws JsonParseException, JsonMappingException, IOException {

		return new View("index.jsp", "inventoryItems",readmodel.getInventoryItems());
	}
	
	public View details(Map params) {
		UUID id = getId(params);
		
		return new View("details.jsp", "inventoryItem", readmodel.getInventoryItemDetails(id));
	}

	public View add(Map params) {
		String name= ((String[]) params.get("name"))[0];

		bus.send(new CreateInventoryItem(UUID.randomUUID(), name));

		return new View("index.do");
	}
	
	public View add() {
		return new View("add.jsp");
	}
	
	public View changeNamePage(Map params) {
		UUID id = getId(params);

		readmodel.getInventoryItemDetails(id);
		return new View("rename.jsp", "inventoryItem", readmodel.getInventoryItemDetails(id));
	}
	public View changeName(Map params) {
		UUID id = getId(params);
		//
		String newName= ((String[]) params.get("newName"))[0];
		//
		int version = getVersion(params);
		//
		bus.send(new RenameInventoryItem(id, newName, version));

		return new View("index.do");
	}

	public View deactivate(Map params) {
		UUID id = getId(params);
		int version = getVersion(params);
		bus.send(new DeactivateInventoryItem(id, version));
		
		return new View("index.do");
	}
	
	public View checkInPage(Map params) {
		UUID id = getId(params);

		readmodel.getInventoryItemDetails(id);
		return new View("checkin.jsp", "inventoryItem", readmodel.getInventoryItemDetails(id));
		
	}
	public View checkIn(Map params) {
		UUID id = getId(params);
		int version = getVersion(params);
		int count = getInt("count",params);
		
		bus.send(new CheckInItemsToInventory(id, count, version));
		
		return new View("index.do");
		
	}
	public View removePage(Map params) {
		UUID id = getId(params);

		readmodel.getInventoryItemDetails(id);
		return new View("remove.jsp", "inventoryItem", readmodel.getInventoryItemDetails(id));
		
	}
	public View remove(Map params) {
		UUID id = getId(params);
		int version = getVersion(params);
		int count = getInt("count",params);
		
		bus.send(new RemoveItemsFromInventory(id, count, version));
		
		return new View("index.do");
		
	}

	private UUID getId(Map params) {
		String stringId= ((String[]) params.get("id"))[0];
		UUID id = UUID.fromString(stringId);
		return id;
	}
	
	private int getVersion(Map params) {
		String stringVersion = ((String[]) params.get("version"))[0];
		int version = Integer.parseInt(stringVersion);
		
		return version;
	}
	private int getInt(String name,Map params) {
		String stringNumber = ((String[]) params.get(name))[0];
		int number = Integer.parseInt(stringNumber);
		
		return number;
	}

}

