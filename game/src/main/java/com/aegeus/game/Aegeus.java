package com.aegeus.game;

import com.aegeus.game.commands.entity.*;
import com.aegeus.game.commands.item.CommandCreate;
import com.aegeus.game.commands.item.CommandGenerate;
import com.aegeus.game.commands.social.*;
import com.aegeus.game.commands.world.CommandAddOre;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AEGEUS Game
 *
 * @since 2016/08/19
 */
public class Aegeus extends JavaPlugin {
	public static Gson GSON;
	private static Aegeus instance;
	private final Map<Location, Material> ores = new HashMap<>();
	private final Map<Entity, AgEntity> entities = new HashMap<>();
	private List<Spawner> spawners = new ArrayList<>();
	private static WorldEditPlugin worldedit;

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
		// wooOOOOOOOOO, loading up!
		getLogger().info("AEGEUS enabling...");
		saveDefaultConfig();

		// LOaDin uO GsOn SeriAliZiNg aNd DeseriAlizinG adapters!
		GsonBuilder b = new GsonBuilder();
		b.setPrettyPrinting();
		b.registerTypeAdapter(Spawner.class, new SpawnerSerializer());
		b.registerTypeAdapter(Spawner.class, new SpawnerDeserializer());
		GSON = b.create();

		//Register APIS
        worldedit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");

		// Register plugin events
		getLogger().info("Registering event listener...");
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new CombatListener(this), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(this), this);
		getServer().getPluginManager().registerEvents(new FishingListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		getServer().getPluginManager().registerEvents(new SpawnerListener(this), this);
		getServer().getPluginManager().registerEvents(new StatsListener(this), this);
		getServer().getPluginManager().registerEvents(new MiningListener(this), this);

		// Register commands
		getLogger().info("Registering commands...");

		// entity
		getCommand("mob").setExecutor(new CommandMob());
		getCommand("spawner").setExecutor(new CommandSpawner());
		getCommand("openentity").setExecutor(new CommandOpenEntity());
		getCommand("showspawners").setExecutor(new CommandShowSpawners());
		getCommand("hidespawners").setExecutor(new CommandHideSpawners());

		// item
		getCommand("create").setExecutor(new CommandCreate());
		getCommand("generate").setExecutor(new CommandGenerate());

		// social
		getCommand("chatchannel").setExecutor(new CommandChatChannel());
		getCommand("global").setExecutor(new CommandGlobal());
		getCommand("message").setExecutor(new CommandMessage());
		getCommand("party").setExecutor(new CommandParty());
		getCommand("roll").setExecutor(new CommandRoll());
		getCommand("broadcast").setExecutor(new CommandBroadcast());

		// world
		getCommand("addore").setExecutor(new CommandAddOre());
		getCommand("createdungeon").setExecutor(new CommandCreateDungeon());

		// Done, done, and done!
		getLogger().info("Load complete.");

		// Post loading
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			// Load game data
			getLogger().info("Loading game data...");
			loadSpawners();
			loadOres();

			getLogger().info("Post-load complete.");
		});
	}

	@Override
	public void onDisable() {
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

	public void addOre(Block b) {
		Location temp = b.getLocation();
		ores.put(new Location(temp.getWorld(), temp.getX(), temp.getY(), temp.getZ()), b.getType());
		saveOres();
	}

	public void removeOre(Location l) {
		ores.remove(l);
		saveOres();
	}

	public void removeSpawner(Location location) {
		spawners.remove(getSpawner(location));
		saveSpawners();
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public Map<Location, Material> getOres() {
		return ores;
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

	public void saveOres() {
		try (FileWriter f = new FileWriter(getDataFolder() + "/ores.json")) {
			for (Location l : ores.keySet()) {
				f.write(ores.get(l).toString() + " " + l.getX() + " " + l.getY() + " " + l.getZ() + " " + l.getWorld().toString() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadOres() {
		try (BufferedReader r = new BufferedReader(new FileReader(getDataFolder() + "/ores.json"))) {
			while (r.ready()) {
				String[] line = r.readLine().split(" ");
				ores.put(new Location(getServer().getWorld(line[4].substring(line[4].indexOf("=") + 1, line[4].indexOf("}"))), Double.valueOf(line[1]), Double.valueOf(line[2]), Double.valueOf(line[3])), Material.valueOf(line[0]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
