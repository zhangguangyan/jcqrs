package cqrs.mr.readModel.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import cqrs.core.Handler;
import cqrs.mr.events.InventoryItemCreated;
import cqrs.mr.readModel.InventoryItemDetailsDto;

public class InventoryItemCreatedHandler implements Handler<InventoryItemCreated> {
	private MongoClient mongo;
	private ObjectMapper mapper = new ObjectMapper();

	public InventoryItemCreatedHandler(MongoClient mongo) {
		this.mongo = mongo;
	}

	@Override
	public void handle(InventoryItemCreated message) {
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		try {
			String item = mapper.writeValueAsString(new InventoryItemDetailsDto(message.id, message.name, 0, 0));
			DBObject dbObject = (DBObject)JSON.parse(item);
			items.insert(dbObject);
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
