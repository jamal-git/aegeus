package com.aegeus.game.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.CraftingCompendium;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * Created by Silvre on 8/23/2017.
 */
public class AegeusPlayerDeserializer implements JsonDeserializer<AgPlayer> {
    @Override
    public AgPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        AgPlayer p = new AgPlayer(Bukkit.getPlayer(UUID.fromString(o.get("uuid").getAsString())));
        p.setLevel(o.get("level").getAsInt());
        p.setXp(o.get("xp").getAsInt());
        p.setSoulpoints(o.get("soulpoints").getAsInt());
        p.setLogins(o.get("logins").getAsInt());
        p.setCraftingCompendium(Aegeus.GSON.fromJson(o.get("compendium"), new TypeToken<CraftingCompendium>(){}
        .getType()));
        return p;
    }
}
