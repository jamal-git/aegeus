package com.aegeus.game;

import com.aegeus.game.commands.*;
import com.aegeus.game.commands.test.CommandTestArmor;
import com.aegeus.game.commands.test.CommandTestMob;
import com.aegeus.game.commands.test.CommandTestPickaxe;
import com.aegeus.game.commands.test.CommandTestWeapon;
import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.entity.Spawner;
import com.aegeus.game.listener.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private static Aegeus instance;
	private Map<LivingEntity, AgEntity> entityData = new HashMap<>();
	private List<Spawner> spawners = new ArrayList<>();
    public static HashMap<Location, Material> ores = new HashMap<>();

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
		getLogger().info("Registering event listener...");
		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		getServer().getPluginManager().registerEvents(new CombatListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new SpawnerListener(this), this);
		getServer().getPluginManager().registerEvents(new StatsListener(this), this);
		getServer().getPluginManager().registerEvents(new MiningListener(this), this);

		// Register game commands
		getLogger().info("Registering commands...");
		getCommand("chatchannel").setExecutor(new CommandChatChannel());
		getCommand("global").setExecutor(new CommandGlobal());
		getCommand("message").setExecutor(new CommandMessage());
		getCommand("roll").setExecutor(new CommandRoll());
		getCommand("spawner").setExecutor(new CommandSpawner());
		getCommand("addore").setExecutor(new CommandAddOre());

		// Register test commands
		getLogger().info("Registering test commands...");
		getCommand("testarmor").setExecutor(new CommandTestArmor());
		getCommand("testweapon").setExecutor(new CommandTestWeapon());
		getCommand("testmob").setExecutor(new CommandTestMob());
		getCommand("testpickaxe").setExecutor(new CommandTestPickaxe());

		// Load spawners
		//loadSpawners();
        loadOres();

		// Clear entities
		getLogger().info("Clearing entities...");
		Bukkit.getWorlds().forEach(w -> w.getLivingEntities().forEach(Entity::remove));

		// Done, done, and done!
		getLogger().info("AEGEUS enabled.");
	}

	@Override
	public void onDisable() {
        getLogger().info("AEGEUS disabled.");
	}

	public AgEntity getEntity(LivingEntity entity) {
		if (!entityData.containsKey(entity))
			entityData.put(entity, new AgEntity(entity));
		return entityData.get(entity);
	}

	public AgPlayer getPlayer(Player player) {
		if (!entityData.containsKey(player))
			entityData.put(player, new AgPlayer(player));
		else if (!(entityData.get(player) instanceof AgPlayer))
			entityData.put(player, new AgPlayer(entityData.get(player), player));
		return (AgPlayer) entityData.get(player);
	}

	public AgMonster getMonster(LivingEntity entity) {
		if (!entityData.containsKey(entity))
			entityData.put(entity, new AgMonster(entity));
		else if (!(entityData.get(entity) instanceof AgMonster))
			entityData.put(entity, new AgMonster(entityData.get(entity)));
		return (AgMonster) entityData.get(entity);
	}

	public Spawner getSpawner(Location location) {
		return spawners.stream().filter(s -> s.getLocation().equals(location))
				.findAny().orElse(null);
	}

	public void addSpawner(Spawner spawner) {
		spawners.add(spawner);
		saveSpawners();
	}

	public void removeEntity(LivingEntity entity) {
		entityData.remove(entity);
	}

	public void removeSpawner(Location location) {
		spawners.remove(getSpawner(location));
		saveSpawners();
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public List<AgEntity> getEntities() {
		return new ArrayList<>(entityData.values());
	}

    public void addOre(Block b) {
	    Location temp = b.getLocation();
	    ores.put(new Location(temp.getWorld(), temp.getX(), temp.getY(), temp.getZ()), b.getType());
	    saveOres();
    }

    public Map<Location, Material> getOres()    {
	    return ores;
    }

    public void removeOre(Location l)  {
	    ores.remove(l);
	    saveOres();
    }

	public void saveOres()  {
        try(FileWriter f = new FileWriter(getDataFolder() + "/ores.json")) {
            for(Location l : ores.keySet()) {
                f.write(ores.get(l).toString() + " " + l.getX() + " " + l.getY() + " " + l.getZ() + " " + l.getWorld().toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadOres()  {
	    try(BufferedReader r = new BufferedReader(new FileReader(getDataFolder() + "/ores.json")))  {
	        while(r.ready())    {
	            String[] line = r.readLine().split(" ");
	            ores.put(new Location(getServer().getWorld(line[4].substring(line[4].indexOf("=") + 1, line[4].indexOf("}"))), Double.valueOf(line[1]), Double.valueOf(line[2]), Double.valueOf(line[3])), Material.valueOf(line[0]));
            }
        }
        catch(IOException e)    {
	        e.printStackTrace();
        }
        getLogger().info(ores.toString());
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
			spawners = GSON.fromJson(fr, new TypeToken<List<Spawner>>() {
			}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
