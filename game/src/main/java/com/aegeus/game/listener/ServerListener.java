package com.aegeus.game.listener;

import com.aegeus.common.Common;
import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.concurrent.ThreadLocalRandom;

public class ServerListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private static final String[] motds = { // List of MOTDs
			"Is that supposed to be a meme?",
			"Not supported by Atlas!",
			"What are you, a miner?",
			"pls, no ddos, am only 8",
			"5.2k Armor Energy, Scouts!",
			"You have to see to believe!",
			"<none>",
			"&mBeloved&7 Known by Gio himself!",
			"Fancy item!",
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
			"Contrary to popular belief, Silvre did not kill it.",
			"There's something different about this place...",
			"Reina best girl, no doubt."
	};
	private final Aegeus parent;

//	private int buffLootTime = 0;
//	private int rebootTime = 7200;

	public ServerListener(Aegeus parent) {
		this.parent = parent;
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

	@SuppressWarnings("deprecation")
	@EventHandler
	// Login messages and initial player setup
	private void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		AgPlayer info = parent.getPlayer(player);
		e.setJoinMessage("");
		player.setHealthScaled(true);
		player.setHealthScale(20);

		Util.updateStats(player);

		if (!player.hasPlayedBefore()) player.sendTitle("", Util.colorCodes("&bWelcome."));
		else player.sendTitle("", Util.colorCodes("&fWelcome back."));

		Bukkit.getScheduler().runTaskLater(parent, () -> {
			for (int i = 0; i < 10; i++)
				player.sendMessage(" ");
			player.sendMessage(Util.colorCodes(
					"          &bAegeus &f&lMMORPG&f\n" +
							"          &b• &7Patch &b" + Common.PATCH + " &7(&o" + Common.PATCH_NOTE + "&7)\n" +
							"          &7Modify game settings with &b/settings"));
			for (int i = 0; i < 2; i++)
				player.sendMessage(" ");
			if (Bukkit.getOnlinePlayers().size() == 1)
				player.sendMessage(Util.colorCodes(
						"&8That's strange. It's quiet around here, like everyone has gone away. Why's that..? " +
								"Have you arrived early, or is the universe arriving late?"
				));
			player.sendMessage(" ");
		}, 2);
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		Util.updateStats(player);
		player.setHealth(player.getMaxHealth());
	}

	@EventHandler
	// Clear user information and punish combat loggers
	private void onLogout(PlayerQuitEvent e) {
		e.setQuitMessage("");
	}

	@EventHandler
	// Random, custom MOTDs
	private void onServerListPing(ServerListPingEvent e) {
		if (Bukkit.hasWhitelist()) e.setMotd(Util.colorCodes(
				"&bAegeus &f&lMMORPG&7 - Patch &b" + Common.PATCH + "\n&f"
						+ "&cUndergoing maintenance. Stay tuned!"));
		else e.setMotd(Util.colorCodes(
				"&bAegeus &f&lMMORPG&7 - Patch &b" + Common.PATCH + "\n&f"
						+ motds[random.nextInt(motds.length)]));
	}

	@EventHandler
	// Disable crafting
	private void onCraft(CraftItemEvent e) {
		e.setCancelled(true);
	}

}
