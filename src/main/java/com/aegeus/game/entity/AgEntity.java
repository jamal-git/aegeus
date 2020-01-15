package com.aegeus.game.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.UUID;

/**
 * An entity wrapper containing Aegeus data.
 *
 * @author oopsjpeg
 */
public class AgEntity {
    private final UUID uniqueId;
    private String gameName;

    public AgEntity(Entity e) {
        uniqueId = e.getUniqueId();
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Entity getEntity() {
        return Bukkit.getEntity(getUniqueId());
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AgEntity && ((AgEntity) obj).getUniqueId().equals(getUniqueId()))
                || (obj instanceof Entity && ((Entity) obj).getUniqueId().equals(getUniqueId()));
    }
}
