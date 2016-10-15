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
import org.bukkit.scoreboard.DisplaySlot;

import com.aegeus.game.data.Data;
import com.aegeus.game.data.EntityData;
import com.aegeus.game.data.PlayerData;
import com.aegeus.game.item.Armor;
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
					.setDisplayName(Helper.colorCodes("&c�?�"));
			Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		// Set BelowNameHP
		Bukkit.getScoreboardManager().getMainScoreboard().getObjective("hp").getScore(player.getName())
				.setScore((int) Math.round(player.getHealth()));
		// Set HP BossBar
		pd.getBossBarHp().setProgress(player.getHealth() / player.getMaxHealth());
		pd.getBossBarHp().setTitle(Helper.colorCodes(
				"&a" + Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()) + " &lHP"));
	}

}
