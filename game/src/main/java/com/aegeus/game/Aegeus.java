package com.aegeus.game;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aegeus.common.Constants;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Aegeus.class);
	public static SimpleCommandMap cmdMap = new SimpleCommandMap(Bukkit.getServer());
	
	/**
	 * 
	 * Created by oopsjpeg and Silvre.
	 * There's probably one or two boats in this project.
	 * M 8, D 19, Y 2016
	 * 
	 */
	@Override
	public void onEnable() {	
		// wooOOOOOOOOO, loading up!
		LOGGER.info("AEGEUS enabling...");
		saveDefaultConfig();
		
		//INTIALIZE THE FUCKING BOT, AEGEUS.
		//try {
			//bot = new AegeusBot();
		//} catch (DiscordException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		
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
		
		// Register test commands
		if(Constants.DEBUG) {
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
