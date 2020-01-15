package com.aegeus.game;

import com.aegeus.game.item.drop.DropSet;
import com.aegeus.game.util.EntityMap;
import com.aegeus.game.util.IntRange;
import com.aegeus.game.util.Util;
import com.google.gson.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.Collection;

public class Spawner {
    private final EntityMap entities = Aegeus.getInstance().getEntities();

    private Location location;
    private EntityType[] entityTypes;
    private IntRange amountToSpawn;
    private int radius;
    private DropSet dropSet;
    private int minPieces;
    private long delay; // in milliseconds
    private boolean removeOnEscape;

    private long lastSpawn;

    public Spawner(Location location, EntityType[] entityTypes, IntRange amountToSpawn, int radius, DropSet dropSet, int minPieces, int delay, boolean removeOnEscape) {
        this.location = location;
        this.entityTypes = entityTypes;
        this.amountToSpawn = amountToSpawn;
        this.radius = radius;
        this.dropSet = dropSet;
        this.minPieces = minPieces;
        this.delay = delay;
        this.removeOnEscape = removeOnEscape;
    }

    public void spawn() {
        for (int i = 0; i < amountToSpawn.get(); i++) {
            EntityType type = entityTypes[Util.RANDOM.nextInt(entityTypes.length)];
            LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(new Location(
                    location.getWorld(),
                    location.getX() + Util.nextInt((radius / 2) * -1, radius / 2),
                    location.getY(),
                    location.getZ() + Util.nextInt((radius / 2) * -1, radius / 2)
            ), type);
            entities.getOrCreateLiving(entity).setSource(this);
            dropSet.equip(entity, minPieces);
        }
        setLastSpawn(System.currentTimeMillis());
    }

    public boolean canSpawn() {
        Collection<Entity> nearby = location.getWorld().getNearbyEntities(location, 30, 30, 30);
        return nearby.stream()
                // Players are nearby
                .anyMatch(entity -> entity instanceof Player)
                // Spawn delay is complete
                && Math.abs(System.currentTimeMillis() - lastSpawn) >= delay
                // Spawner has no more children
                && nearby.stream()
                .filter(entity -> entity instanceof LivingEntity)
                .filter(entity -> !(entity instanceof Player))
                .map(entity -> (LivingEntity) entity)
                .filter(entities::contains)
                .noneMatch(entity -> this.equals(entities.getLiving(entity).getSource()));
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EntityType[] getEntityTypes() {
        return entityTypes;
    }

    public void setEntityTypes(EntityType[] entityTypes) {
        this.entityTypes = entityTypes;
    }

    public IntRange getAmountToSpawn() {
        return amountToSpawn;
    }

    public void setAmountToSpawn(IntRange amountToSpawn) {
        this.amountToSpawn = amountToSpawn;
    }

    public DropSet getDropSet() {
        return dropSet;
    }

    public void setDropSet(DropSet dropSet) {
        this.dropSet = dropSet;
    }

    public int getMinPieces() {
        return minPieces;
    }

    public void setMinPieces(int minPieces) {
        this.minPieces = minPieces;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean getRemoveOnEscape() {
        return removeOnEscape;
    }

    public void setRemoveOnEscape(boolean removeOnEscape) {
        this.removeOnEscape = removeOnEscape;
    }

    public double getLastSpawn() {
        return lastSpawn;
    }

    public void setLastSpawn(long t) {
        lastSpawn = t;
    }

    public static class Adapter implements JsonDeserializer<Spawner> {
        @Override
        public Spawner deserialize(JsonElement src, Type typeOfSrc, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = src.getAsJsonObject();

            Location location = Aegeus.GSON.fromJson(object.get("location"), Location.class);
            EntityType[] entityTypes = Aegeus.GSON.fromJson(object.get("entityTypes"), EntityType[].class);
            IntRange amountToSpawn = Aegeus.GSON.fromJson(object.get("amountToSpawn"), IntRange.class);
            int radius = object.get("radius").getAsInt();
            DropSet dropSet = Aegeus.getInstance().getDropSets().get(object.get("dropSet").getAsString());
            int minPieces = object.get("minPieces").getAsInt();
            int delay = object.get("delay").getAsInt();
            boolean removeOnEscape = object.get("removeOnEscape").getAsBoolean();

            return new Spawner(location, entityTypes, amountToSpawn, radius, dropSet, minPieces, delay, removeOnEscape);
        }
    }
}
