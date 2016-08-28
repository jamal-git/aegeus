package com.aegeus.aegeus;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.aegeus.aegeus.bot.AegeusBot;
import com.aegeus.aegeus.game.Bank;
import com.aegeus.aegeus.game.Combat;
import com.aegeus.aegeus.game.Server;
import com.aegeus.aegeus.game.Statistics;
import com.aegeus.aegeus.game.chat.Chat;
import com.aegeus.aegeus.game.commands.CommandChatChannel;
import com.aegeus.aegeus.game.commands.CommandGlobal;
import com.aegeus.aegeus.game.commands.CommandMessage;
import com.aegeus.aegeus.game.commands.CommandRoll;
import com.aegeus.aegeus.game.commands.CommandSpaceCart;
import com.aegeus.aegeus.game.commands.CommandSpawnPick;
import com.aegeus.aegeus.game.commands.CommandPlanet;
import com.aegeus.aegeus.game.commands.test.CommandTestArmor;
import com.aegeus.aegeus.game.commands.test.CommandTestWeapon;
import com.aegeus.aegeus.game.mining.Mining;

import sx.blah.discord.util.DiscordException;

public class Aegeus extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger(Aegeus.class.getName());
	public static AegeusBot bot;
	
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
		log.log(Level.INFO, "Aegeus enabling...");
		saveDefaultConfig();
		
		//INTIALIZE THE FUCKING BOT, AEGEUS.
		try {
			bot = new AegeusBot();
		} catch (DiscordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Register plugin events
		// TODO Clean up a bit?
		log.log(Level.INFO, "Registering events...");
		getServer().getPluginManager().registerEvents(new Server(this), this);
		getServer().getPluginManager().registerEvents(new Combat(this), this);
		getServer().getPluginManager().registerEvents(new Chat(this), this);
//		getServer().getPluginManager().registerEvents(new Mobs(this), this);
		getServer().getPluginManager().registerEvents(new Mining(this), this);
		getServer().getPluginManager().registerEvents(new Statistics(this), this);
		getServer().getPluginManager().registerEvents(new Bank(this), this);
		
		// Register plugin commands
		log.log(Level.INFO, "Registering commands...");
		getCommand("chatchannel").setExecutor(new CommandChatChannel());
		getCommand("global").setExecutor(new CommandGlobal());
		getCommand("message").setExecutor(new CommandMessage());
		getCommand("planet").setExecutor(new CommandPlanet());
		getCommand("roll").setExecutor(new CommandRoll());
		getCommand("testarmor").setExecutor(new CommandTestArmor());
		getCommand("testweapon").setExecutor(new CommandTestWeapon());
		getCommand("spacecart").setExecutor(new CommandSpaceCart());
		getCommand("spawnpick").setExecutor(new CommandSpawnPick());
		
		// Done, done, and done!
		getLogger().log(Level.INFO, "Aegeus enabled.");
	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "Aegeus disabled.");
	}
}
