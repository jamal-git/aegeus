package com.aegeus.game;

import com.aegeus.game.command.CreateCommand;
import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.util.EntityBox;
import com.aegeus.game.listener.CombatListener;
import com.aegeus.game.listener.EntityListener;
import com.aegeus.game.listener.PlayerListener;
import com.aegeus.game.tools.ToolListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Aegeus extends JavaPlugin {
	private static Aegeus instance;

	private final EntityBox entities = new EntityBox();

	private MongoMaster mongo;

	public static Aegeus getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		// Create the singleton!
		instance = this;

		// wooOOOOOOOOO, loading up!
		getLogger().info("Aegeus is enabling...");
		saveDefaultConfig();

		// Register plugin events
		getLogger().info("Registering event listeners...");
		getServer().getPluginManager().registerEvents(new CombatListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new ToolListener(), this);

		// Register commands
		getLogger().info("Registering commands...");
		getCommand("create").setExecutor(new CreateCommand());

		// Done, done, and done!
		getLogger().info("Aegeus is enabled.");

		// Post loading
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			// Load resources from mongo
			openMongo();
			mongo.loadTiersConfig();
			// Ultra done!
			getLogger().info("Post-load completed.");
		});
	}

	@Override
	public void onDisable() {
		for (AgEntity e : getEntities())
			e.getEntity().remove();
		getLogger().info("Aegeus is disabled.");
	}

	public void openMongo() {
		getLogger().info("Loading database...");
		// Connect to the mongo server
		mongo = new MongoMaster(getConfig().getString("mongo"));
		// Disconnect the mongo client when shutting down
		Runtime.getRuntime().addShutdownHook(new Thread(mongo::close));
		getLogger().info("Loaded database.");
	}

	public EntityBox getEntities() {
		return entities;
	}
}
