package cqrs.mr.ui

import java.util
import java.util.UUID

import cqrs.core.{Event, EventPublisher}
import cqrs.core.bus.{CommandBus, EventBus}
import cqrs.core.eventstore.InMemoryEventStore
import cqrs.mr.commandhandlers._
import cqrs.mr.commands._
import cqrs.mr.domain.InventoryItem
import cqrs.mr.events._
import cqrs.mr.infra.RepositoryImpl
import cqrs.mr.readModel.detailsview.DetailsView
import cqrs.mr.readModel.{InventoryItemDetailsDto, InventoryItemListDto, ListView, ReadModelFacade}
import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val eventBus = new EventBus()
  val detailsView = new DetailsView()
  eventBus.registerHandler(classOf[InventoryItemCreated], detailsView.createInventoryItemCreatedHandler());
  eventBus.registerHandler(classOf[InventoryItemDeactivated], detailsView.createInventoryItemDeactivatedHandler());
  eventBus.registerHandler(classOf[InventoryItemRenamed], detailsView.createInventoryItemRenamedHandler());
  eventBus.registerHandler(classOf[ItemsCheckedInToInventory], detailsView.createItemsCheckedInToInventoryHandler());
  eventBus.registerHandler(classOf[ItemsRemovedFromInventory], detailsView.createItemsRemovedFromInventoryHandler());

  //--event handlers: list view
  val listView = new ListView();
  eventBus.registerHandler(classOf[InventoryItemCreated], listView.createInventoryItemCreatedHandler());
  eventBus.registerHandler(classOf[InventoryItemRenamed], listView.createInventoryItemRenamedHandler());
  eventBus.registerHandler(classOf[InventoryItemDeactivated], listView.createInventoryItemDeactivatedHandler());

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
    def name = request.body("name")(0)
    bus.send(new CreateInventoryItem(UUID.randomUUID, name))
    Redirect("index.do")
  }

  def changeName() = Action(parse.formUrlEncoded) { request =>
    def id = request.body("id")(0)
    def name = request.body("newName")(0)
    val version: String = request.body("version")(0)
    bus.send(new RenameInventoryItem(UUID.fromString(id), name, version.toInt))
    Redirect("index.do")
  }

  def deactivate() = Action(parse.formUrlEncoded) { request =>
    def id = request.body("id")(0)
    def version = request.body("version")(0)
    bus.send(new DeactivateInventoryItem(UUID.fromString(id), version.toInt))
    Redirect("index.do")
  }

  def checkIn() = Action(parse.formUrlEncoded) { request =>
    def id = request.body("id")(0)
    def version = request.body("version")(0)
    def count = request.body("count")(0)
    bus.send(new CheckInItemsToInventory(UUID.fromString(id), count.toInt, version.toInt))
    Redirect("index.do")
  }

  def checkOut() = Action(parse.formUrlEncoded) { request =>
    def id = request.body("id")(0)
    def version = request.body("version")(0)
    def count = request.body("count")(0)
    bus.send(new RemoveItemsFromInventory(UUID.fromString(id), count.toInt, version.toInt))
    Redirect("index.do")
  }
}
