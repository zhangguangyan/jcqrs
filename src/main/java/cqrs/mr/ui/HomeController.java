package cqrs.mr.ui;

import cqrs.core.bus.CommandBus;
import cqrs.mr.commands.*;
import cqrs.mr.readModel.ReadModelFacade;
import cqrs.mr.ui.mvc.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;

@Controller
public class HomeController {
	private final CommandBus bus;
	private final ReadModelFacade readmodel;

	@Autowired
	public HomeController(CommandBus bus, ReadModelFacade readModel) {
		this.bus = bus;
		this.readmodel = readModel;
	}

	@RequestMapping("/index.do")
	public String index(Model model) {
        model.addAttribute("inventoryItems", readmodel.getInventoryItems());
        return "index";
	}

	@RequestMapping("/details.do")
	public ModelAndView details(@RequestParam("id") String id) {
		UUID uuid = UUID.fromString(id);
        ModelMap map = new ModelMap();
        map.put("inventoryItem", readmodel.getInventoryItemDetails(uuid));
        return new ModelAndView("details", map);
	}

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("name") String name) {
        bus.send(new CreateInventoryItem(UUID.randomUUID(), name));

        ModelMap map = new ModelMap();
        map.put("inventoryItems", readmodel.getInventoryItems());
        return new ModelAndView("index", map);
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add() {
		//return new View("add.jsp");
        return "add";
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

