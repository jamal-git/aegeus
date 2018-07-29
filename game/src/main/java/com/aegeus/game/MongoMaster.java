package com.aegeus.game;

import com.aegeus.game.item.Stats;
import com.aegeus.game.item.Tiers;
import com.aegeus.game.util.IntRange;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MongoMaster extends MongoClient {
	private final MongoDatabase database = getDatabase("aegeus");
	private final MongoCollection<Document> configs = database.getCollection("configs");

	public MongoMaster(String host) {
		super(host);
	}

	public void loadTiersConfig() {
		Document doc = configs.find(Filters.eq("_id", "tiers")).first();

		if (doc != null) {
			Document docDura = (Document) doc.get("dura");
			List<Document> docDefStats = (ArrayList<Document>) doc.get("def_stats");

			// Load dura values
			if (docDura != null) {
				Tiers.Dura.setArmor((List<Integer>) docDura.get("armor"));
				Tiers.Dura.setWeapon((List<Integer>) docDura.get("weapon"));
			}

			// Load default stats
			if (docDefStats != null)
				Tiers.DefStats.setStats(docDefStats.stream().map(docStats -> {
					Stats stats = new Stats();
					List<Document> weapons = (ArrayList<Document>) docStats.get("weapons");

					stats.setWeapons(weapons.stream().map(docWep -> new Stats.Weapon(
							Material.getMaterial(docWep.getString("material")),
							new IntRange(docWep.getInteger("min_dmg"), docWep.getInteger("max_dmg")),
							new IntRange(0, docWep.getInteger("range"))))
							.collect(Collectors.toList()));

					return stats;
				}).collect(Collectors.toList()));
		}
	}
}
