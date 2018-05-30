package cqrs.mr.ui

import java.util
import java.util.UUID

import cqrs.core.bus.{CommandBus, EventBus}
import cqrs.core.eventstore.InMemoryEventStore
import cqrs.mr.commandhandlers._
import cqrs.mr.commands._
import cqrs.mr.domain.InventoryItem
import cqrs.mr.events._
import cqrs.mr.infra.RepositoryImpl
import cqrs.mr.readModel._
import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val eventBus = new EventBus()
  eventBus.registerHandler(classOf[InventoryItemCreated], DetailsView.createInventoryItemCreatedHandler())
  eventBus.registerHandler(classOf[InventoryItemDeactivated], DetailsView.createInventoryItemDeactivatedHandler())
  eventBus.registerHandler(classOf[InventoryItemRenamed], DetailsView.createInventoryItemRenamedHandler())
  eventBus.registerHandler(classOf[ItemsCheckedInToInventory], DetailsView.createItemsCheckedInToInventoryHandler())
  eventBus.registerHandler(classOf[ItemsRemovedFromInventory], DetailsView.createItemsRemovedFromInventoryHandler())

  //--event handlers: list view
  eventBus.registerHandler(classOf[InventoryItemCreated], ListView.createInventoryItemCreatedHandler())
  eventBus.registerHandler(classOf[InventoryItemRenamed], ListView.createInventoryItemRenamedHandler())
  eventBus.registerHandler(classOf[InventoryItemDeactivated], ListView.createInventoryItemDeactivatedHandler())

  val eventStore = new InMemoryEventStore(eventBus)
  val repo = new RepositoryImpl[InventoryItem](eventStore)

  val bus = new CommandBus()
  bus.registerHandler(classOf[CreateInventoryItem], new CreateInventoryItemHandler(repo))
  bus.registerHandler(classOf[CheckInItemsToInventory], new CheckInItemsToInventoryHandler(repo))
  bus.registerHandler(classOf[DeactivateInventoryItem], new DeactivateInventoryItemHandler(repo))
  bus.registerHandler(classOf[RemoveItemsFromInventory], new RemoveItemsFromInventoryHandler(repo))
  bus.registerHandler(classOf[RenameInventoryItem], new RenameInventoryItemHandler(repo))

  val readModel: ReadModelFacade = new ReadModelFacade()

  def index = Action {
    val items: util.List[InventoryItemListDto] = readModel.getInventoryItems()
    Ok(cqrs.mr.ui.html.index(items))
  }

  def showAddPage = Action {
    Ok(cqrs.mr.ui.html.add())
  }

  def detailsPage(id: String) = Action {
    val uuid = UUID.fromString(id)
    val item: InventoryItemDetailsDto = readModel.getInventoryItemDetails(uuid)
    Ok(cqrs.mr.ui.html.details(item))
  }

  def renamePage(id: String) = Action {
    val uuid = UUID.fromString(id)
    val item = readModel.getInventoryItemDetails(uuid)
    Ok(cqrs.mr.ui.html.rename(item))
  }

  def checkInPage(id: String) = Action {
    val uuid = UUID.fromString(id)
    val item = readModel.getInventoryItemDetails(uuid)
    Ok(cqrs.mr.ui.html.checkin(item))
  }

  def removePage(id: String) = Action {
    val uuid = UUID.fromString(id)
    val item = readModel.getInventoryItemDetails(uuid)
    Ok(cqrs.mr.ui.html.remove(item))
  }

  def add() = Action(parse.formUrlEncoded) { request =>
    val name = request.body("name").head
    bus.send(new CreateInventoryItem(UUID.randomUUID, name))
    Redirect("index.do")
  }

  def changeName() = Action(parse.formUrlEncoded) { request =>
    val id = request.body("id").head
    val name = request.body("newName").head
    val version: String = request.body("version").head
    bus.send(new RenameInventoryItem(UUID.fromString(id), name, version.toInt))
    Redirect("index.do")
  }

  def deactivate() = Action(parse.formUrlEncoded) { request =>
    val id = request.body("id").head
    val version = request.body("version").head
    bus.send(new DeactivateInventoryItem(UUID.fromString(id), version.toInt))
    Redirect("index.do")
  }

  def checkIn() = Action(parse.formUrlEncoded) { request =>
    val id = request.body("id").head
    val version = request.body("version").head
    val count = request.body("count").head
    bus.send(new CheckInItemsToInventory(UUID.fromString(id), count.toInt, version.toInt))
    Redirect("index.do")
  }

  def checkOut() = Action(parse.formUrlEncoded) { request =>
    val id = request.body("id").head
    val version = request.body("version").head
    val count = request.body("count").head

    bus.send(new RemoveItemsFromInventory(UUID.fromString(id), count.toInt, version.toInt))
    Redirect("index.do")
  }
}
