package com.aegeus.game;

import com.aegeus.game.commands.*;
import com.aegeus.game.commands.test.*;
import com.aegeus.game.entity.*;
import com.aegeus.game.listener.*;
import com.aegeus.game.util.SpawnerDeserializer;
import com.aegeus.game.util.SpawnerSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
	private final Map<LivingEntity, AgEntity> entities = new HashMap<>();
	private final Map<Projectile, AgProjectile> projectiles = new HashMap<>();
	private List<Spawner> spawners = new ArrayList<>();

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

		// LOaDin uO GsOn SeriAliZiNg aNd DeseriAlizinG adapters!
		GsonBuilder b = new GsonBuilder();
		b.setPrettyPrinting();
		b.registerTypeAdapter(Spawner.class, new SpawnerSerializer());
		b.registerTypeAdapter(Spawner.class, new SpawnerDeserializer());
		GSON = b.create();

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

		// Register game commands
		getLogger().info("Registering commands...");
		getCommand("chatchannel").setExecutor(new CommandChatChannel());
		getCommand("global").setExecutor(new CommandGlobal());
		getCommand("message").setExecutor(new CommandMessage());
		getCommand("openentity").setExecutor(new CommandOpenEntity());
		getCommand("roll").setExecutor(new CommandRoll());
		getCommand("spawner").setExecutor(new CommandSpawner());
		getCommand("addore").setExecutor(new CommandAddOre());
		getCommand("showspawners").setExecutor(new CommandShowSpawners());
		getCommand("hidespawners").setExecutor(new CommandHideSpawners());

		// Register test commands
		getLogger().info("Registering test commands...");
		getCommand("testarmor").setExecutor(new CommandTestArmor());
		getCommand("testenchant").setExecutor(new CommandTestEnchant());
		getCommand("testweapon").setExecutor(new CommandTestWeapon());
		getCommand("testrod").setExecutor(new CommandTestRod());
		getCommand("testmob").setExecutor(new CommandTestMob());
		getCommand("testpickaxe").setExecutor(new CommandTestPickaxe());
		getCommand("testrune").setExecutor(new CommandTestRune());

		// Done, done, and done!
		getLogger().info("AEGEUS enabled.");

		// Post loading
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			// Load game data
			getLogger().info("Loading game data...");
			loadSpawners();
			loadOres();
		});
	}

	@Override
	public void onDisable() {
		getLogger().info("AEGEUS disabled.");
	}

	public AgEntity getEntity(LivingEntity entity) {
		if (!entities.containsKey(entity))
			entities.put(entity, new AgEntity(entity));
		return entities.get(entity);
	}

	public AgPlayer getPlayer(Player player) {
		if (!entities.containsKey(player))
			entities.put(player, new AgPlayer(player));
		else if (!(entities.get(player) instanceof AgPlayer))
			entities.put(player, new AgPlayer(entities.get(player), player));
		return (AgPlayer) entities.get(player);
	}

	public AgMonster getMonster(LivingEntity entity) {
		if (!entities.containsKey(entity))
			entities.put(entity, new AgMonster(entity));
		else if (!(entities.get(entity) instanceof AgMonster))
			entities.put(entity, new AgMonster(entities.get(entity)));
		return (AgMonster) entities.get(entity);
	}

	public AgProjectile getProjectile(Projectile p) {
		if (!projectiles.containsKey(p))
			projectiles.put(p, new AgProjectile(p));
		return projectiles.get(p);
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

	public void removeEntity(LivingEntity entity) {
		entities.remove(entity);
	}

	public void removeSpawner(Location location) {
		spawners.remove(getSpawner(location));
		saveSpawners();
	}

	public void removeProjectile(Projectile p) {
		projectiles.remove(p);
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public List<AgEntity> getEntities() {
		return new ArrayList<>(entities.values());
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
