package com.aegeus.game;

import com.aegeus.game.entity.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Aegeus extends JavaPlugin {
	private static Aegeus instance;
	private final List<AgEntity> entities = new ArrayList<>();

	public static Aegeus getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		// Create the singleton!
		instance = this;

		// wooOOOOOOOOO, loading up!
		getLogger().info("AEGEUS enabling...");
		saveDefaultConfig();

		// Register plugin events
		getLogger().info("Registering event listeners...");

		// Register commands
		getLogger().info("Registering commands...");

		// Done, done, and done!
		getLogger().info("Load complete.");

		// Post loading
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			getLogger().info("Post-load complete.");
		});
	}

	@Override
	public void onDisable() {
		getLogger().info("AEGEUS disabled.");
		entities.forEach(this::remove);
	}

	public List<AgEntity> getEntities() {
		return entities;
	}

	public AgEntity get(Entity e) {
		if (!entities.contains(e))
			entities.add(new AgEntity(e));
		return entities.get(entities.indexOf(e));
	}

	public AgPlayer getPlayer(Player p) {
		return (AgPlayer) get(p);
	}

	public AgLiving getLiving(LivingEntity e) {
		return (AgLiving) get(e);
	}

	public AgMonster getMonster(LivingEntity e) {
		return (AgMonster) get(e);
	}

	public AgProjectile getProjectile(Projectile p) {
		return (AgProjectile) get(p);
	}

	public void remove(Entity e) {
		e.remove();
		entities.remove(e);
	}

	public void remove(AgEntity e) {
		remove(e.getEntity());
	}
}
