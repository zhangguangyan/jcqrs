package cqrs.mr.readModel.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import cqrs.core.Handler;
import cqrs.mr.events.ItemsRemovedFromInventory;

public class ItemsRemovedFromInventoryHandler implements
		Handler<ItemsRemovedFromInventory> {

	private MongoClient mongo;

	public ItemsRemovedFromInventoryHandler(MongoClient mongo) {
		this.mongo = mongo;
	}

	@Override
	public void handle(ItemsRemovedFromInventory message) {
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		items.findAndModify(new BasicDBObject("id", message.id.toString()), 
			new BasicDBObject("$set", new BasicDBObject("currentCount", message.count).append("version", message.version)));
	}
}
