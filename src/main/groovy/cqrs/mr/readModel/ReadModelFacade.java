package cqrs.mr.readModel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class ReadModelFacade {
	private MongoClient mongo;
	private ObjectMapper mapper = new ObjectMapper();

	public ReadModelFacade(MongoClient mongo) {
		this.mongo = mongo;
	}

	public List<InventoryItemListDto> getInventoryItems() throws JsonParseException, JsonMappingException, IOException {
		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		BasicDBObject keys = new BasicDBObject("_id", 0)
			.append("name", 1);
		DBCursor all = items.find(new BasicDBObject(), keys);
		return mapper.readValue(JSON.serialize(all.toArray()), new TypeReference<List<InventoryItemListDto>>() {});
	}

	public InventoryItemDetailsDto getInventoryItemDetails(UUID id) {
		//return new InventoryItemDetailsDto(id,"name",10,1);
		return BullShitDatabase.details.get(id);
	}
}
