package com.aegeus.game;

import com.aegeus.game.commands.entity.*;
import com.aegeus.game.commands.item.CommandCreate;
import com.aegeus.game.commands.item.CommandGenerate;
import com.aegeus.game.commands.item.CommandRepair;
import com.aegeus.game.commands.social.*;
import com.aegeus.game.commands.world.CommandCreateDungeon;
import com.aegeus.game.entity.*;
import com.aegeus.game.listener.*;
import com.aegeus.game.util.SpawnerDeserializer;
import com.aegeus.game.util.SpawnerSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * AEGEUS Game
 *
 * @since 2016/08/19
 */
public class Aegeus extends JavaPlugin {
	public static Gson GSON;
	private static Aegeus instance;
	private static WorldEditPlugin worldedit;
	private final Map<Entity, AgEntity> entities = new HashMap<>();
	private List<Spawner> spawners = new ArrayList<>();

	public static Aegeus getInstance() {
		return instance;
	}

	public static WorldEditPlugin getWorldEdit()    {
	    return worldedit;
    }

	@Override
	public void onEnable() {
		// Create the singleton!
		instance = this;
		WorldManager.getInstance().initialize();
		// wooOOOOOOOOO, loading up!
		getLogger().info("AEGEUS enabling...");
		saveDefaultConfig();

		// LOaDin uP GsOn SeriAliZiNg aNd DeseriAlizinG adapters!
		GsonBuilder b = new GsonBuilder();
		b.setPrettyPrinting();
		b.registerTypeAdapter(Spawner.class, new SpawnerSerializer());
		b.registerTypeAdapter(Spawner.class, new SpawnerDeserializer());
		GSON = b.create();

		//Register APIS
        worldedit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");

		// Register plugin events
		getLogger().info("Registering event listeners...");
		getServer().getPluginManager().registerEvents(new MiscListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new CombatListener(this), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		getServer().getPluginManager().registerEvents(new SpawnerListener(this), this);
		getServer().getPluginManager().registerEvents(new StatsListener(this), this);
		getServer().getPluginManager().registerEvents(new MiningListener(this), this);
		getServer().getPluginManager().registerEvents(new DungeonListener(this), this);

		// Register commands
		getLogger().info("Registering commands...");

		// entity
        getCommand("mount").setExecutor(new CommandMount());
		getCommand("mob").setExecutor(new CommandMob());
		getCommand("spawner").setExecutor(new CommandSpawner());
		getCommand("openentity").setExecutor(new CommandOpenEntity());
		getCommand("showspawners").setExecutor(new CommandShowSpawners());
		getCommand("hidespawners").setExecutor(new CommandHideSpawners());

		// item
		getCommand("create").setExecutor(new CommandCreate());
		getCommand("generate").setExecutor(new CommandGenerate());
		getCommand("repair").setExecutor(new CommandRepair());

		// social
		getCommand("chatchannel").setExecutor(new CommandChatChannel());
		getCommand("global").setExecutor(new CommandGlobal());
		getCommand("message").setExecutor(new CommandMessage());
		getCommand("party").setExecutor(new CommandParty());
		getCommand("roll").setExecutor(new CommandRoll());
		getCommand("broadcast").setExecutor(new CommandBroadcast());

		// world
		getCommand("createdungeon").setExecutor(new CommandCreateDungeon());

		// Done, done, and done!
		getLogger().info("Load complete.");

		// Post loading
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			// Load game data
			getLogger().info("Loading game data...");
			loadSpawners();

			getLogger().info("Post-load complete.");
		});
	}

	@Override
	public void onDisable() {
        WorldManager.getInstance().deleteDungeons();
	    getLogger().info("AEGEUS disabled.");
	}

	public AgEntity get(Entity e) {
		if (!contains(e)) add(e);
		return entities.get(e);
	}

	public AgPlayer getPlayer(Player p) {
		AgEntity info = get(p);
		if (!(info instanceof AgPlayer))
			info = put(p, new AgPlayer(info));
		return (AgPlayer) info;
	}

	public AgLiving getLiving(LivingEntity e) {
		AgEntity info = get(e);
		if (!(info instanceof AgLiving))
			info = put(e, new AgLiving(info));
		return (AgLiving) info;
	}

	public AgMonster getMonster(LivingEntity e) {
		AgEntity info = get(e);
		if (!(info instanceof AgMonster))
			info = put(e, new AgMonster(info));
		return (AgMonster) info;
	}

	public AgProjectile getProjectile(Projectile p) {
		AgEntity info = get(p);
		if (!(info instanceof AgProjectile))
			info = put(p, new AgProjectile(info));
		return (AgProjectile) info;
	}

	public void add(Entity e) {
		if (!contains(e)) entities.put(e, new AgEntity(e));
	}

	public void add(AgEntity e) {
		add(e.getEntity());
	}

	public void remove(Entity e) {
		entities.remove(e);
	}

	public void remove(AgEntity e) {
		remove(e.getEntity());
	}

	public boolean contains(Entity e) {
		return entities.containsKey(e);
	}

	public boolean contains(AgEntity e) {
		return entities.containsValue(e);
	}

	public AgEntity put(Entity e, AgEntity info) {
		entities.put(e, info);
		return entities.get(e);
	}

	public Spawner getSpawner(Location location) {
		return spawners.stream().filter(s -> s.getLocation().equals(location))
				.findAny().orElse(null);
	}

	public void addSpawner(Spawner spawner) {
		spawners.add(spawner);
		saveSpawners();
	}

	public void removeSpawner(Location location) {
		spawners.remove(getSpawner(location));
		saveSpawners();
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public Map<Entity, AgEntity> getEntityMap() {
		return entities;
	}

	public Collection<AgEntity> getEntities() {
		return entities.values();
	}

	public void saveSpawners() {
		try (FileWriter fw = new FileWriter(getDataFolder() + "/spawners.json")) {
			GSON.toJson(spawners, fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSpawners() {
		try (FileReader fr = new FileReader(getDataFolder() + "/spawners.json")) {
			spawners = GSON.fromJson(fr, new TypeToken<ArrayList<Spawner>>() {
			}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
