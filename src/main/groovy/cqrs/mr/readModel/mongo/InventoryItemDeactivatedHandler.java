package cqrs.mr.readModel.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemDeactivated;

public class InventoryItemDeactivatedHandler implements Handler<InventoryItemDeactivated> {

	private MongoClient mongo;

	public InventoryItemDeactivatedHandler(MongoClient mongo) {
		this.mongo = mongo;
	}

	@Override
	public void handle(InventoryItemDeactivated message) {
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		items.findAndRemove(new BasicDBObject("id", message.id.toString()));
	}
}