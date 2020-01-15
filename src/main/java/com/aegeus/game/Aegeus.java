package com.aegeus.game;

import com.aegeus.game.command.CreateCommand;
import com.aegeus.game.command.HealCommand;
import com.aegeus.game.command.RollCommand;
import com.aegeus.game.item.drop.Drop;
import com.aegeus.game.item.drop.DropSet;
import com.aegeus.game.listener.CombatListener;
import com.aegeus.game.listener.PlayerListener;
import com.aegeus.game.util.Adapters;
import com.aegeus.game.util.EntityMap;
import com.aegeus.game.util.IntRange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class Aegeus extends JavaPlugin {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(IntRange.class, new IntRange.Adapter())
            .registerTypeAdapter(Spawner.class, new Spawner.Adapter())
            .registerTypeAdapter(Location.class, new Adapters.Location())
            .registerTypeAdapter(EntityType.class, new Adapters.EntityType())
            .registerTypeAdapterFactory(Drop.ADAPTER_FACTORY)
            .setPrettyPrinting().create();
    private static Aegeus instance;

    private final EntityMap entities = new EntityMap();
    private final List<Spawner> spawners = new ArrayList<>();
    private final Map<String, DropSet> dropSets = new HashMap<>();

    public static Aegeus getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Create singleton
        instance = this;

        // Loading up!
        getLogger().info("Aegeus is enabling...");
        saveDefaultConfig();

        // Register plugin events
        getLogger().info("Registering event listeners...");
        getServer().getPluginManager().registerEvents(new CombatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Register commands
        getLogger().info("Registering commands...");
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("create").setExecutor(new CreateCommand());
        getCommand("roll").setExecutor(new RollCommand());

        // Done!
        getLogger().info("Aegeus is enabled.");

        // Post load
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            // Load resources
            loadDropSets();
            loadSpawners();

            // Schedule spawners
            Bukkit.getScheduler().runTaskTimer(this, () -> spawners.stream()
                    .filter(Spawner::canSpawn)
                    .forEach(Spawner::spawn), 200, 30);

            // Schedule entity source escaping
            Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getWorlds()
                    .forEach(world -> world.getLivingEntities().stream()
                            .filter(entities::contains)
                            .map(entities::getLiving)
                            .filter(data -> data.hasSource() && !data.isNearSource())
                            .forEach(data -> {
                                if (data.getSource().getRemoveOnEscape())
                                    entities.remove(data);
                                else
                                    data.getEntity().teleport(data.getSource().getLocation());
                            })), 200, 60);

            // Ultra done!
            getLogger().info("Post-load completed.");
        });
    }

    @Override
    public void onDisable() {
        entities.wipe();
        getLogger().info("Aegeus is disabled.");
    }

    public EntityMap getEntities() {
        return entities;
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }

    public Map<String, DropSet> getDropSets() {
        return dropSets;
    }

    private void loadDropSets() {
        try (FileReader fr = new FileReader(getDataFolder() + "\\dropsets.json")) {
            dropSets.clear();
            dropSets.putAll(GSON.fromJson(fr, new TypeToken<Map<String, DropSet>>(){}.getType()));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void loadSpawners() {
        try (FileReader fr = new FileReader(getDataFolder() + "\\spawners.json")) {
            spawners.clear();
            spawners.addAll(Arrays.asList(GSON.fromJson(fr, Spawner[].class)));
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
