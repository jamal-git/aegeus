package com.aegeus.game.listener;

import com.aegeus.common.Common;
import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.concurrent.ThreadLocalRandom;

public class ServerListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Aegeus parent;

	public ServerListener(Aegeus parent) {
		this.parent = parent;
	}

	@EventHandler
	// Login messages and initial player setup
	private void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		AgPlayer info = parent.getPlayer(player);
		e.setJoinMessage("");
		player.setHealthScaled(true);
		player.setHealthScale(20);

		Util.updateStats(player);

		if (!player.hasPlayedBefore()) //noinspection deprecation
			player.sendTitle("", Util.colorCodes("&bWelcome."));
		else //noinspection deprecation
			player.sendTitle("", Util.colorCodes("&fWelcome back."));

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
						+ "Work in progress."));
	}

	@EventHandler
	// Disable crafting
	private void onCraft(CraftItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
	}

	@EventHandler
	private void onWorldLoad(WorldLoadEvent e) {
		// Clear entities
		Aegeus.getInstance().getLogger().info("Cleaning world '" + e.getWorld().getName() + "'...");
		e.getWorld().getLivingEntities().stream().filter(a -> !(a instanceof Player)).forEach(Entity::remove);
	}

}
