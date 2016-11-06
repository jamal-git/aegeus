package com.aegeus.game;

import com.aegeus.game.chat.Chat;
import com.aegeus.game.commands.*;
import com.aegeus.game.commands.test.CommandTestArmor;
import com.aegeus.game.commands.test.CommandTestMob;
import com.aegeus.game.commands.test.CommandTestWeapon;
import com.aegeus.game.mining.Mining;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * AEGEUS Game
 *
 * @since 2016/08/19
 */
public class Aegeus extends JavaPlugin {
	public final Logger LOGGER = getLogger();

	@Override
	public void onEnable() {
		// wooOOOOOOOOO, loading up!
		LOGGER.info("AEGEUS enabling...");
		saveDefaultConfig();

		// Register plugin events
		LOGGER.info("Registering event listeners...");
		getServer().getPluginManager().registerEvents(new Server(this), this);
		getServer().getPluginManager().registerEvents(new Combat(this), this);
		getServer().getPluginManager().registerEvents(new Chat(this), this);
		getServer().getPluginManager().registerEvents(new Mobs(this), this);
		getServer().getPluginManager().registerEvents(new Mining(this), this);
		getServer().getPluginManager().registerEvents(new Statistics(this), this);
		getServer().getPluginManager().registerEvents(new Bank(this), this);

		// Register game commands
		LOGGER.info("Registering commands...");
		getCommand("chatchannel").setExecutor(new CommandChatChannel());
		getCommand("global").setExecutor(new CommandGlobal());
		getCommand("message").setExecutor(new CommandMessage());
		getCommand("planet").setExecutor(new CommandPlanet());
		getCommand("roll").setExecutor(new CommandRoll());
		getCommand("spacecart").setExecutor(new CommandSpaceCart());
		getCommand("spawnpick").setExecutor(new CommandSpawnPick());
		getCommand("spawner").setExecutor(new CommandSpawner());

		// Register test commands
		LOGGER.info("Registering test commands...");
		getCommand("testarmor").setExecutor(new CommandTestArmor());
		getCommand("testweapon").setExecutor(new CommandTestWeapon());
		getCommand("testmob").setExecutor(new CommandTestMob());

		// Done, done, and done!
		LOGGER.info("AEGEUS enabled.");
	}

	@Override
	public void onDisable() {
		LOGGER.info("AEGEUS disabled.");
	}
}
