package cqrs.mr.readModel.mongo;

import java.net.UnknownHostException;
import java.util.UUID;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import cqrs.mr.events.InventoryItemCreated;

public class InventoryItemCreatedHandlerTest {

	@Test
	public void testHandle() throws UnknownHostException {
		MongoClient mongo = new MongoClient();
		InventoryItemCreatedHandler h = new InventoryItemCreatedHandler(mongo);
		h.handle(new InventoryItemCreated(UUID.randomUUID(), "aaaaa"));
		
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		DBObject item = items.findOne();
		
		System.out.println(JSON.serialize(item));
	}

}
