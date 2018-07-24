package com.aegeus.game;

import com.aegeus.game.item.util.Tiers;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Map;

public class MongoMaster extends MongoClient {
	private final MongoDatabase database = getDatabase("aegeus");
	private final MongoCollection<Document> configs = database.getCollection("configs");

	public MongoMaster(String host) {
		super(host);
	}

	public void loadTiersConfig() {
		Document doc = configs.find(Filters.eq("_id", "tiers")).first();

		Document dura = (Document) doc.get("dura");
		Tiers.Durability.setArmor((Map<Integer, Integer>) dura.get("armor"));
		Tiers.Durability.setWeapon((Map<Integer, Integer>) dura.get("weapon"));
	}
}
