package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import com.aegeus.game.stats.Stats;
import com.google.gson.*;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silvre on 7/4/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class SpawnerDeserializer implements JsonDeserializer<Spawner> {
	@Override
	public Spawner deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject o = jsonElement.getAsJsonObject();
		Location l = new Location(Aegeus.getInstance().getServer().getWorld(
				o.get("world").getAsString()),
				o.get("x").getAsInt(),
				o.get("y").getAsInt(),
				o.get("z").getAsInt());
		Spawner s = new Spawner(l);
		List<Stats> list = new ArrayList<>();
		JsonArray a = o.get("list").getAsJsonArray();
		for (JsonElement e : a) {
			try {
				list.add((Stats) Class.forName(e.getAsString()).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		s.setList(list);
		return s;
	}
}
