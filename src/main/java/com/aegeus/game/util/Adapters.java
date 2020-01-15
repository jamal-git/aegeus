package com.aegeus.game.util;

import com.google.gson.*;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;

public class Adapters {
    public static class EntityType implements JsonDeserializer<org.bukkit.entity.EntityType> {
        @Override
        public org.bukkit.entity.EntityType deserialize(JsonElement src, Type typeOfSrc, JsonDeserializationContext context)
                throws JsonParseException {
            return org.bukkit.entity.EntityType.valueOf(src.getAsString());
        }
    }

    public static class Location implements JsonDeserializer<org.bukkit.Location> {
        @Override
        public org.bukkit.Location deserialize(JsonElement src, Type typeOfSrc, JsonDeserializationContext context)
                throws JsonParseException {
            JsonArray array = src.getAsJsonArray();
            return new org.bukkit.Location(
                    Bukkit.getWorld(array.get(0).getAsString()), // world
                    array.get(1).getAsDouble(), // x
                    array.get(2).getAsDouble(), // y
                    array.get(3).getAsDouble()); // z
        }
    }
}