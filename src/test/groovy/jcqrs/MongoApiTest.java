package jcqrs;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import cqrs.mr.readModel.InventoryItemDetailsDto;
import cqrs.mr.readModel.InventoryItemListDto;

public class MongoApiTest {

	@Test
	public void test1() throws JsonParseException, JsonMappingException, IOException {
		MongoClient mongo = new MongoClient();
		ObjectMapper mapper = new ObjectMapper();

		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		BasicDBObject keys = new BasicDBObject("_id", 0)
			.append("name", 1);
		DBCursor all = items.find(new BasicDBObject(), keys);
		InventoryItemListDto[] results = mapper.readValue(JSON.serialize(all.toArray()), InventoryItemListDto[].class);
		
		System.out.println(mapper.readValue(JSON.serialize(all.toArray()), new TypeReference<List<InventoryItemListDto>>() {}));

		/*
		 * com.fasterxml.jackson.databind.JsonMappingException: No suitable constructor found for type [simple type, class cqrs.mr.readModel.InventoryItemListDto]: can not instantiate from JSON object (need to add/enable type information?)
 at [Source: [ { "name" : "aaaaa"}]; line: 1, column: 5] (through reference chain: Object[][0])
	at com.fasterxml.jackson.databind.JsonMappingException.from(JsonMappingException.java:148)
	at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromObjectUsingNonDefault(BeanDeserializerBase.java:1063)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:264)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:124)
	at com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer.deserialize(ObjectArrayDeserializer.java:149)
	at com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer.deserialize(ObjectArrayDeserializer.java:17)
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:3051)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2146)
	at jcqrs.MongoApiTest.test(MongoApiTest.java:33)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:483)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
		 */
	}
	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		MongoClient mongo = new MongoClient();
		ObjectMapper mapper = new ObjectMapper();

		DB db = mongo.getDB("inventory");
		DBCollection items = db.getCollection("items");
		BasicDBObject keys = new BasicDBObject("_id", 0);

		DBObject item = items.findOne(new BasicDBObject("id", "45091d84-ac87-4bbe-be5e-0e5c56712698"), keys);
		InventoryItemDetailsDto itemDetails = mapper.readValue(JSON.serialize(item), InventoryItemDetailsDto.class);
		
		System.out.println(itemDetails.getName());
	}
}
