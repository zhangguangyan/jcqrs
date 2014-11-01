package cqrs.mr.readModel.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemRenamed;

public class InventoryItemRenamedHandler implements
		Handler<InventoryItemRenamed> {
	private MongoClient mongo;
	
	public InventoryItemRenamedHandler(MongoClient mongo) {
		this.mongo = mongo;
	}

	@Override
	public void handle(InventoryItemRenamed message) {
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		items.findAndModify(new BasicDBObject("id", message.id.toString()), 
				new BasicDBObject("$set", new BasicDBObject("name", message.newName).append("version", message.version)));
	}
}
