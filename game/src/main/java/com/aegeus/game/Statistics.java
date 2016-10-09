package com.aegeus.game;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import com.aegeus.game.item.ItemArmor;
import com.aegeus.game.player.EntityData;
import com.aegeus.game.player.PlayerData;
import com.aegeus.game.util.Helper;

public class Statistics implements Listener {

//	private JavaPlugin parent;

	public Statistics(JavaPlugin parent) {
//		this.parent = parent;
//		new BukkitRunnable(){
//			@Override
//			public void run() {
//				for(Player player : parent.getServer().getOnlinePlayers()){
//					if(PlayerData.get(player).InCombat.isBefore(LocalDateTime.now().minusSeconds(15))
//							&& player.getHealth() < player.getMaxHealth()
//							&& !player.isDead()){
//						double hp = player.getHealth();
//						double maxhp = player.getMaxHealth();
//						hp += (5 + PlayerData.get(player).getHPRegen());
//						if(hp > maxhp){
//							hp = maxhp;
//						}
//						player.setHealth(hp);
//						updateDisplay(player);
//					}
//				}
//			}
//		}.runTaskTimer(parent, 0, 20);
	}

	/**
	 * Updates the statistics of an entity.
	 * 
	 * @param entity Entity to update.
	 */
	public static void updateStats(LivingEntity entity) {
		List<ItemArmor> items = new ArrayList<>();
		int hp = 5;
		int hpRegen = 0;
		int energyRegen = 0;
//		int nstr = 0;
//		int ndef = 0;
//		int nvit = 0;
//		int nmagres = 0;
//		int nthorns = 0;
//		int ngoldfind = 0;
		
		if (entity.getEquipment().getHelmet() != null
				&& entity.getEquipment().getHelmet().getType() != Material.AIR)
			items.add(new ItemArmor(entity.getEquipment().getHelmet()));
		
		if (entity.getEquipment().getChestplate() != null
				&& entity.getEquipment().getChestplate().getType() != Material.AIR)
			items.add(new ItemArmor(entity.getEquipment().getChestplate()));
		
		if (entity.getEquipment().getLeggings() != null
				&& entity.getEquipment().getLeggings().getType() != Material.AIR)
			items.add(new ItemArmor(entity.getEquipment().getLeggings()));
		
		if (entity.getEquipment().getBoots() != null
				&& entity.getEquipment().getBoots().getType() != Material.AIR)
			items.add(new ItemArmor(entity.getEquipment().getBoots()));
		

		if(!items.isEmpty())
			for(ItemArmor item : items){
				hp += item.getHp();
				hpRegen = item.getHpRegen();
				//energyRegen += armor.getEnergyRegen();
			}
		
		if (entity.getType().equals(EntityType.PLAYER)) {
			hp += 95;
		}

		entity.setMaxHealth(hp);
		EntityData.get(entity).setHPRegen(hpRegen);
		EntityData.get(entity).setEnergyRegen(energyRegen);
		
		if (entity.getType().equals(EntityType.PLAYER)) {
			updateDisplay((Player) entity);
		}
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
		PlayerData data = PlayerData.get(player);
		if (data.getBossBarHP() == null) {
			// Create a new Hp BossBar for this player
			data.setBossBarHP(Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SEGMENTED_20));
			data.getBossBarHP().addPlayer(player);
			data.getBossBarHP().setVisible(true);
		}
		if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp") == null) {
			// Create an objective for BelowNameHP
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective("hp", "health");
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp")
					.setDisplayName(Helper.colorCodes("&c�?�"));
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		// Set BelowNameHP
		Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").getScore(player.getName())
				.setScore((int) Math.round(player.getHealth()));
		// Set HP BossBar
		data.getBossBarHP().setProgress(player.getHealth() / player.getMaxHealth());
		data.getBossBarHP().setTitle(Helper.colorCodes(
				"&a" + Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()) + " &lHP"));
	}

}
