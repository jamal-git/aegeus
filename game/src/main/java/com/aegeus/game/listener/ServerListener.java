package com.aegeus.game.listener;

import com.aegeus.common.Common;
import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.Rarity;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.social.Party;
import com.aegeus.game.social.PartyManager;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Arrays;
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
		if(!parent.contains(player) && parent.hasPlayerData(player))
		    parent.loadPlayer(player);
        AgPlayer info = parent.getPlayer(player);
		info.addLogins(1);
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
			player.sendMessage(Util.colorCodes("&bCurrent number of logins: &e&l" + info.getLogins()));
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

		Weapon w = new Weapon(Material.WOOD_SWORD);
		w.setName("&fTraining Dagger");
		w.setTier(1);
		w.setRarity(Rarity.STARTER);
		w.setDmg(4, 6);
		player.getInventory().addItem(w.build());

		if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType().equals(Material.AIR)) {
			Armor a = new Armor(Material.LEATHER_HELMET);
			a.setName("&fTraining Helmet");
			a.setTier(1);
			a.setRarity(Rarity.STARTER);
			a.setHp(9);
			a.setHpRegen(2);
			player.getInventory().setHelmet(a.build());
		}

		if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType().equals(Material.AIR)) {
			Armor a = new Armor(Material.LEATHER_CHESTPLATE);
			a.setName("&fTraining Chestplate");
			a.setTier(1);
			a.setRarity(Rarity.STARTER);
			a.setHp(13);
			a.setHpRegen(3);
			player.getInventory().setChestplate(a.build());
		}

		if (player.getInventory().getLeggings() == null || player.getInventory().getLeggings().getType().equals(Material.AIR)) {
			Armor a = new Armor(Material.LEATHER_LEGGINGS);
			a.setName("&fTraining Leggings");
			a.setTier(1);
			a.setRarity(Rarity.STARTER);
			a.setHp(13);
			a.setHpRegen(3);
			player.getInventory().setLeggings(a.build());
		}

		if (player.getInventory().getBoots() == null || player.getInventory().getBoots().getType().equals(Material.AIR)) {
			Armor a = new Armor(Material.LEATHER_BOOTS);
			a.setName("&fTraining Boots");
			a.setTier(1);
			a.setRarity(Rarity.STARTER);
			a.setHp(9);
			a.setHpRegen(2);
			player.getInventory().setBoots(a.build());
		}

		Util.updateStats(player);
		player.setHealth(player.getMaxHealth());
		Util.updateDisplay(player);
	}

	@EventHandler
	// Clear user information and punish combat loggers
	private void onLogout(PlayerQuitEvent e) {
		AgPlayer player = Aegeus.getInstance().getPlayer(e.getPlayer());
        Party p = PartyManager.getInstance().getPartyFromPlayer(player);
        if(p != null) p.remove(player);
        parent.savePlayer(player);
		e.setQuitMessage("");
	}

	@EventHandler
	// Random, custom MOTDs
	private void onServerListPing(ServerListPingEvent e) {
		String first = "&bAegeus &f&lMMORPG&7 [Patch &3" + Common.PATCH + "&7]\n";
		if (Bukkit.hasWhitelist()) e.setMotd(Util.colorCodes(first
				+ "&eMaintenance currently in progress."));
		else {
			AgPlayer info = Aegeus.getInstance().getEntities().stream().filter(i -> i instanceof AgPlayer
					&& ((AgPlayer) i).getPlayer().getAddress().getAddress().equals(e.getAddress()))
					.map(i -> (AgPlayer) i).findAny().orElse(null);
			if (info != null) e.setMotd(Util.colorCodes(first
					+ "Hello, &b&l" + info.getPlayer().getName() + "&b!&f"
					+ " &7[&3&lLV&3 " + (info.getLevel() + 1) + "&7]"));
			else e.setMotd(Util.colorCodes(first
					+ "Begin your adventure, &b&lTODAY&b!"));
		}
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
	private void onChunkLoad(ChunkLoadEvent e) {
		// Clear entities
		Arrays.stream(e.getChunk().getEntities()).filter(a ->
				a instanceof LivingEntity && !(a instanceof Player))
				.forEach(Entity::remove);
	}

}
