package com.aegeus.game;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import com.aegeus.game.chat.Chat;
import com.aegeus.game.commands.*;
import com.aegeus.game.commands.test.*;
import com.aegeus.game.mining.Mining;

/**
 * AEGEUS Game
 * @category MMORPG
 * @since 2016/08/19
 */
public class Aegeus extends JavaPlugin {
	
	private final Logger LOGGER = getLogger();
	private final boolean DEBUG = true;
	public static SimpleCommandMap cmdMap = new SimpleCommandMap(Bukkit.getServer());
	
	@Override
	public void onEnable() {	
		// wooOOOOOOOOO, loading up!
		LOGGER.info("AEGEUS enabling...");
		saveDefaultConfig();
		
		// Register plugin events
		// TODO Clean up a bit?
		LOGGER.info("Registering events...");
		getServer().getPluginManager().registerEvents(new Server(this), this);
		getServer().getPluginManager().registerEvents(new Combat(this), this);
		getServer().getPluginManager().registerEvents(new Chat(this), this);
//		getServer().getPluginManager().registerEvents(new Mobs(this), this);
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
		getCommand("codex").setExecutor(new CommandCodex());
		
		// Register test commands
		if(DEBUG) {
			getCommand("testarmor").setExecutor(new CommandTestArmor());
			getCommand("testweapon").setExecutor(new CommandTestWeapon());
		}
		
		// Done, done, and done!
		LOGGER.info("AEGEUS enabled.");
	}

	@Override
	public void onDisable() {
		LOGGER.info("AEGEUS disabled.");
	}
}
