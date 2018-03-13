package cqrs.mr.ui;

import cqrs.core.bus.CommandBus;
import cqrs.mr.commands.*;
import cqrs.mr.readModel.ReadModelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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



	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add() {
        return "add";
	}

	@RequestMapping("/changeNamePage.do")
	public ModelAndView changeNamePage(@RequestParam("id") String id) {
		UUID uuid = UUID.fromString(id);
		ModelMap map = new ModelMap();
		map.put("inventoryItem", readmodel.getInventoryItemDetails(uuid));
		return new ModelAndView("rename", map);
	}

    @RequestMapping("/checkInPage.do")
    public ModelAndView checkInPage(@RequestParam("id") String id) {
        UUID uuid = UUID.fromString(id);
        ModelMap map = new ModelMap();
        map.put("inventoryItem", readmodel.getInventoryItemDetails(uuid));
        return new ModelAndView("checkin", map);
    }

	@RequestMapping("/removePage.do")
	public ModelAndView removePage(@RequestParam("id") String id) {
		UUID uuid = UUID.fromString(id);
        ModelMap map = new ModelMap();
        map.put("inventoryItem", readmodel.getInventoryItemDetails(uuid));
        return new ModelAndView("remove", map);
		
	}

	/*---- application state change ------------------------*/
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("name") String name) {
        bus.send(new CreateInventoryItem(UUID.randomUUID(), name));
        return toIndexPage();
    }

    @RequestMapping(value = "/changeName.do", method = RequestMethod.POST)
    public ModelAndView changeName(@RequestParam("id") String id, @RequestParam("version") int version, @RequestParam("newName") String newName) {
        UUID uuid = UUID.fromString(id);
        bus.send(new RenameInventoryItem(uuid, newName, version));
        return toIndexPage();
    }

    @RequestMapping(value = "/deactivate.do", method = RequestMethod.POST)
    public ModelAndView deactivate(@RequestParam("id") String id, @RequestParam("version") int version) {
        UUID uuid = UUID.fromString(id);
        bus.send(new DeactivateInventoryItem(uuid, version));
        return toIndexPage();
    }

    @RequestMapping(value = "/checkIn.do", method = RequestMethod.POST)
    public ModelAndView checkIn(@RequestParam("id") String id, @RequestParam("version") int version, @RequestParam("count") int count) {
        UUID uuid = UUID.fromString(id);
        bus.send(new CheckInItemsToInventory(uuid, count, version));
        return toIndexPage();
    }

    @RequestMapping(value = "/remove.do", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam("id") String id, @RequestParam("version") int version, @RequestParam("count") int count) {
        UUID uuid = UUID.fromString(id);
        bus.send(new RemoveItemsFromInventory(uuid, count, version));
        return toIndexPage();
    }

    private ModelAndView toIndexPage() {
        ModelMap map = new ModelMap();
        map.put("inventoryItems", readmodel.getInventoryItems());
        return new ModelAndView("index", map);
    }
}

