package com.aegeus.game.util;

import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.entity.AgProjectile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Map that holds the data from every custom entity.
 * Contains specific methods to handle this data carefully.
 *
 * @author oopsjpeg
 */
public class EntityMap {
    private final HashMap<UUID, AgEntity> map = new HashMap<>();

    public AgEntity get(UUID id) {
        return map.getOrDefault(id, null);
    }

    public AgEntity get(Entity e) {
        return get(e.getUniqueId());
    }

    public AgEntity getOrCreate(UUID id) {
        return getOrCreate(Bukkit.getEntity(id));
    }

    public AgEntity getOrCreate(Entity e) {
        if (!map.containsKey(e.getUniqueId()))
            map.put(e.getUniqueId(), resolve(e));
        return get(e);
    }

    public AgLiving getLiving(LivingEntity e) {
        return (AgLiving) get(e);
    }

    public AgLiving getOrCreateLiving(LivingEntity e) {
        return (AgLiving) getOrCreate(e);
    }

    public AgPlayer getPlayer(Player p) {
        return (AgPlayer) getOrCreate(p);
    }

    public AgProjectile getProjectile(Projectile p) {
        return (AgProjectile) get(p);
    }

    public AgProjectile getOrCreateProjectile(Projectile p) {
        return (AgProjectile) getOrCreate(p);
    }

    public boolean contains(UUID uniqueId) {
        return map.containsKey(uniqueId);
    }

    public boolean contains(Entity entity) {
        return contains(entity.getUniqueId());
    }

    public void remove(UUID uniqueId) {
        if (map.containsKey(uniqueId)) {
            AgEntity data = map.get(uniqueId);
            if (!(data.getEntity() instanceof Player))
                data.getEntity().remove();
            map.remove(uniqueId);
        }
    }

    public void remove(Entity entity) {
        remove(entity.getUniqueId());
    }

    public void remove(AgEntity data) {
        remove(data.getUniqueId());
    }

    public void wipe() {
        for (UUID id : new LinkedList<>(map.keySet())) remove(id);
    }

    private AgEntity resolve(Entity e) {
        if (e instanceof Player) return new AgPlayer((Player) e);
        if (e instanceof Projectile) return new AgProjectile((Projectile) e);
        if (e instanceof LivingEntity) return new AgLiving((LivingEntity) e);
        return new AgEntity(e);
    }
}