package cqrs.mr.readModel.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import cqrs.core.Handler;
import cqrs.mr.events.ItemsCheckedInToInventory;

public class ItemsCheckedInToInventoryHandler implements
		Handler<ItemsCheckedInToInventory> {

	private MongoClient mongo;

	public ItemsCheckedInToInventoryHandler(MongoClient mongo) {
		this.mongo =  mongo;
	}

	@Override
	public void handle(ItemsCheckedInToInventory message) {
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		items.findAndModify(new BasicDBObject("id", message.id.toString()), 
			new BasicDBObject("$set", new BasicDBObject("currentCount", message.count).append("version", message.version)));
	}
}
