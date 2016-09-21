package com.aegeus.aegeus.game;

import java.util.HashMap;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.aegeus.aegeus.player.PlayerData;
import com.aegeus.aegeus.util.Helper;
import static com.aegeus.aegeus.common.Constants.*;

public class Server implements Listener {

//	private JavaPlugin parent;

	public Server(JavaPlugin parent) {
//		this.parent = parent;
//		Runnable checkBySecond = new Runnable() {
//			public void run() {
//				if (buffLootTime > 0) {
//					buffLootTime -= 1;
//					if (buffLootTime <= 0) {
//						Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + ">>" + ChatColor.GOLD
//								+ " Global Loot Buff EXPIRED");
//					}
//				}
//				if (rebootTime > 1) {
//					rebootTime -= 1;
//					if (rebootTime == 1200) {
//						Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + ">>" + ChatColor.GOLD
//								+ " The server will reboot in 20 minutes.");
//					}
//					if (rebootTime == 600) {
//						Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + ">>" + ChatColor.GOLD
//								+ " The server will reboot in 10 minutes.");
//					}
//					if (rebootTime == 300) {
//						Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + ">>" + ChatColor.GOLD
//								+ " The server will reboot in 5 minutes.");
//					}
//					if (rebootTime == 60) {
//						Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + ">>" + ChatColor.GOLD
//								+ " The server will reboot in 1 minute. Use " + ChatColor.BOLD + "/logout"
//								+ ChatColor.GOLD + " to ensure your data is saved.");
//					}
//					if (rebootTime <= 5) {
//						Bukkit.broadcastMessage(
//								ChatColor.RED + "" + ChatColor.BOLD + ">>" + ChatColor.GOLD + " " + rebootTime + "...");
//					}
//					if (rebootTime <= 0) {
//						Bukkit.setWhitelist(true);
//						for (Player p : Bukkit.getOnlinePlayers()) {
//							p.sendMessage(
//									ChatColor.YELLOW + "Attempting to save player data. You should've logged out!");
//						}
//						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spigot:restart");
//					}
//				}
//			}
//		};
//		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//		executor.scheduleAtFixedRate(checkBySecond, 0, 1, TimeUnit.SECONDS);
	}

	public static int buffLootTime = 0;
	public static int rebootTime = 7200;

	public static HashMap<Player, Boolean> playerCombatLog = new HashMap<>();

	private final String[] motds = { // List of MOTDs
			"Is that supposed to be a meme?",
			"Not supported by Atlas!",
			"What are you, a miner?",
			"pls, no ddos, am only 8",
			"5.2k Armor Energy, Scouts!",
			"You have to see to believe!",
			"<none>",
			"&mBeloved&7 Known by Gio himself!",
			"Fancy items!",
			"peppy pls fix",
			"oopspng",
			"I'm from PlanetMinecraft, op please?",
			"There's more where that came from!",
			"No, we aren't serving seconds!",
			"Free T5 on us!",
			"Close your eyes and make a wish!",
			"loads of memes.sk",
			"Divided Nations? What's that?",
			"Easy game, easy life!",
			"Too many daggers!",
			"Kilvre silled it!",
			"It's high noon somewhere in the world.",
			"Contrary to popular belief, Silvre did not kill it."};
			
			

	@EventHandler
	private void onLoginEvent(PlayerLoginEvent event) {
		if (rebootTime <= 300) {
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage("The server is about to reboot.");
		}
	}

	@EventHandler
	private void onCommandEvent(ServerCommandEvent event) {
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.isOp()) player.sendMessage(Helper.colorCodes(
					"&8" + event.getSender().getName() + " executed " + event.getCommand()));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	// Login messages and initial player setup
	// TODO clean this
	private void onJoinEvent(PlayerJoinEvent event) throws InterruptedException {
		Player player = event.getPlayer();
		event.setJoinMessage("");
		player.setHealthScaled(true);
		player.setHealthScale(20);
		if (!player.hasPlayedBefore()) {
			player.sendTitle("", Helper.colorCodes("&aWelcome."));
		} else {
			player.sendTitle("", Helper.colorCodes("&fWelcome back."));
		}
		for (int i = 0; i < 10; i++) {
			player.sendMessage(" ");
		}
		player.sendMessage(Helper.colorCodes(
				"          &aAegeus &f&lMMORPG&f\n" +
				"          &a» &7Build &a" + BUILD + " &7(&o" + BUILD_NOTE + "&7)\n" +
				"          &7Modify game settings with &a/settings"));
		for (int i = 0; i < 3; i++) {
			player.sendMessage(" ");
		}
		Statistics.updateStats(player);
	}

	@EventHandler
	// Clear user information and punish combat loggers
	private void onLogoutEvent(PlayerQuitEvent event) {
		event.setQuitMessage("");
		PlayerData.remove(event.getPlayer());
	}

	@EventHandler
	// Random, custom MOTDs
	private void onServerListPingEvent(ServerListPingEvent event) {
		Random random = new Random();
		if(Bukkit.hasWhitelist()){
			event.setMotd(Helper.colorCodes(
					"&aAegeus &f&lMMORPG&7 - Build &a" + BUILD + "\n"
					+ "&cUngergoing maintenance. Stay tuned!"));
		} else {
			event.setMotd(Helper.colorCodes(
					"&aAegeus &f&lMMORPG&7 - Build &a" + BUILD + "\n"
					+ motds[random.nextInt(motds.length)]));
		}
	}

	@EventHandler
	// Disable crafting
	private void onCraftEvent(CraftItemEvent event) {
		event.setCancelled(true);
	}
}
