package com.aegeus.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import com.aegeus.game.data.Data;
import com.aegeus.game.data.EntityData;
import com.aegeus.game.data.PlayerData;
import com.aegeus.game.item.Armor;
import com.aegeus.game.util.Utility;

public class Statistics implements Listener {

	private JavaPlugin plugin;

	public Statistics(JavaPlugin plugin) {
		this.plugin = plugin;
		new BukkitRunnable(){
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers())
					if(!player.isDead() && player.getHealth() < player.getMaxHealth()) {
						PlayerData pd = Data.getPlayerData(player);
						if(!pd.isInCombat()) {
							double health = player.getHealth();
							double maxHealth = player.getMaxHealth();
							health += pd.getHpRegen();
							if(health > maxHealth) health = maxHealth;
							player.setHealth(health);
						}
					}
			}
		}.runTaskTimer(plugin, 20, 20);
	}

	/**
	 * Updates the statistics of an entity.
	 * 
	 * @param entity Entity to update.
	 */
	public static void updateStats(LivingEntity entity) {
		int hp = 5;
		int hpRegen = 0;
		int energyRegen = 0;
//		int nstr = 0;
//		int ndef = 0;
//		int nvit = 0;
//		int nmagres = 0;
//		int nthorns = 0;
//		int ngoldfind = 0;

		for(ItemStack i : entity.getEquipment().getArmorContents()) {
			if(i != null && i.getType().equals(Material.AIR)) {
				Armor armor = new Armor(i);
				hp += armor.getHp();
				hpRegen = armor.getHpRegen();
				//energyRegen += armor.getEnergyRegen();
			}
		}
		
		if (entity.getType().equals(EntityType.PLAYER))
			hp += 95;

		entity.setMaxHealth(hp);
		EntityData ed = Data.get(entity);
		ed.setHpRegen(hpRegen);
		ed.setEnergyRegen(energyRegen);
		
		if (entity.getType().equals(EntityType.PLAYER))
			updateDisplay((Player) entity);
		
	}

	/**
	 * Updates the statistics of a player.
	 * @param player Player to update.
	 */
	public static void updateStats(Player player) {
		updateStats((LivingEntity) player);
	}

	/**
	 * Updates the statistic display of a player.
	 * @param player Player to update.
	 */
	public static void updateDisplay(Player player) {
		PlayerData pd = Data.getPlayerData(player);
		if (pd.getBossBarHp() == null) {
			// Create a new Hp BossBar for this player
			pd.setBossBarHp(Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SEGMENTED_20));
			pd.getBossBarHp().addPlayer(player);
			pd.getBossBarHp().setVisible(true);
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
		pd.getBossBarHp().setProgress(player.getHealth() / player.getMaxHealth());
		pd.getBossBarHp().setTitle(Utility.colorCodes(
				"&a" + Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()) + " &lHP"));
	}

}
