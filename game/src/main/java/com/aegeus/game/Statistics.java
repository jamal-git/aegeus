package com.aegeus.game;

import com.aegeus.game.data.AegeusEntity;
import com.aegeus.game.data.AegeusPlayer;
import com.aegeus.game.data.Data;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

public class Statistics implements Listener {
	private final Aegeus PARENT;

	public Statistics(Aegeus parent) {
		this.PARENT = parent;
		Bukkit.getScheduler().runTaskTimer(PARENT, () -> {
			for (Player player : Bukkit.getOnlinePlayers())
				if (!player.isDead() && player.getHealth() < player.getMaxHealth()) {
					AegeusPlayer ap = Data.get(player);
					if (!ap.isInCombat()) {
						double health = player.getHealth();
						double maxHealth = player.getMaxHealth();
						health += ap.getHpRegen();
						if (health > maxHealth) health = maxHealth;
						player.setHealth(health);
						updateDisplay(player);
					}
				}
		}, 20, 20);
	}

	public static void updateStats(LivingEntity entity) {
		int hp = 0;
		int hpRegen = 0;
		float energyRegen = 0;
		float defense = 0;
		float magicRes = 0;
		float block = 0;
		int strength = 0;
		int intelligence = 0;
		int vitality = 0;
		int dexterity = 0;

		for (ItemStack i : entity.getEquipment().getArmorContents()) {
			if (i != null && !i.getType().equals(Material.AIR)) {
				Armor armor = new Armor(i);
				hp += armor.getHp();
				hpRegen = armor.getHpRegen();
				energyRegen += armor.getEnergyRegen();
				defense += armor.getDefense();
				magicRes += armor.getMagicRes();
				block += armor.getBlock();
				strength += armor.getEquipmentInfo().getStrength();
				intelligence += armor.getEquipmentInfo().getIntelligence();
				vitality += armor.getEquipmentInfo().getVitality();
			}
		}

		if (entity instanceof Player) {
			Player player = (Player) entity;
			AegeusPlayer ap = Data.get(player);
			hp += 100;
			switch (ap.getLegion()) {
				case FEROCIOUS:
					strength += (strength * 0.1);
					vitality += (vitality * 0.05);
					break;
				case NIMBLE:
					dexterity += (dexterity * 0.1);
					vitality += (vitality * 0.05);
					break;
				case CRYPTIC:
					vitality += (vitality * 0.1);
					intelligence += (intelligence * 0.05);
					break;
				case DIVINE:
					intelligence += (intelligence * 0.1);
					dexterity += (dexterity * 0.05);
					break;
				default:
					break;
			}
		}

		entity.setMaxHealth(hp);
		AegeusEntity ae = Data.get(entity);
		ae.setHpRegen(hpRegen);
		ae.setEnergyRegen(energyRegen);
		ae.setDefense(defense);
		ae.setMagicRes(magicRes);
		ae.setBlock(block);
		ae.setStrength(strength);
		ae.setIntelligence(intelligence);
		ae.setVitality(vitality);
		ae.setDexterity(dexterity);

		if (entity instanceof Player) updateDisplay((Player) entity);
	}

	/**
	 * Updates the statistics of a player.
	 *
	 * @param player Player to update.
	 */
	public static void updateStats(Player player) {
		updateStats((LivingEntity) player);
	}

	/**
	 * Updates the statistic display of a player.
	 *
	 * @param player Player to update.
	 */
	public static void updateDisplay(Player player) {
		AegeusPlayer ap = Data.get(player);
		if (ap.getBossBarHp() == null) {
			// Create a new Hp BossBar for this player
			ap.setBossBarHp(Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SEGMENTED_20));
			ap.getBossBarHp().addPlayer(player);
			ap.getBossBarHp().setVisible(true);
		}
		if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp") == null) {
			// Create an objective for BelowNameHP
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective("hp", "health");
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp")
					.setDisplayName(Utility.colorCodes("&c�?�"));
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		// Set BelowNameHP
		Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").getScore(player.getName())
				.setScore((int) Math.round(player.getHealth()));
		// Set HP BossBar
		ap.getBossBarHp().setProgress(player.getHealth() / player.getMaxHealth());
		ap.getBossBarHp().setTitle(Utility.colorCodes(
				"&a" + Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()) + " &lHP"));
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Bukkit.getScheduler().runTaskLater(PARENT, () -> updateStats(player), 1);
	}

}
