package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.CraftingCompendium;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.UUID;

public class AgPlayerSerializer implements JsonSerializer<AgPlayer>, JsonDeserializer<AgPlayer> {
	@Override
	public JsonElement serialize(AgPlayer player, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject object = new JsonObject();
		object.addProperty("uuid", player.getPlayer().getUniqueId().toString());
		object.addProperty("level", player.getLevel());
		object.addProperty("xp", player.getXp());
		object.addProperty("soulpoints", player.getSoulpoints());
		object.addProperty("logins", player.getLogins()); //todo testing porpoises, remove
		object.add("compendium", Aegeus.GSON.toJsonTree(player.getCraftingCompendium()));
		return object;
	}

	@Override
	public AgPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject o = jsonElement.getAsJsonObject();
		AgPlayer p = new AgPlayer(Bukkit.getPlayer(UUID.fromString(o.get("uuid").getAsString())));
		p.setLevel(o.get("level").getAsInt());
		p.setXp(o.get("xp").getAsInt());
		p.setSoulpoints(o.get("soulpoints").getAsInt());
		p.setLogins(o.get("logins").getAsInt());
		p.setCraftingCompendium(Aegeus.GSON.fromJson(o.get("compendium"), new TypeToken<CraftingCompendium>() {
		}
				.getType()));
		return p;
	}
}
