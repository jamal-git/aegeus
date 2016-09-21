package com.aegeus.aegeus.game;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.aegeus.aegeus.game.item.ItemWeapon;
import com.aegeus.aegeus.player.PlayerData;
import com.aegeus.aegeus.util.Helper;

public class Combat implements Listener {

	private JavaPlugin parent;

	public Combat(JavaPlugin parent) {
		this.parent = parent;
	}

	private static HashMap<Entity, Entity> entityLastHitBy = new HashMap<>();

	@EventHandler
	private void onEntityDeath(EntityDeathEvent event){
		if (entityLastHitBy.get(event.getEntity()) != null){
			if(entityLastHitBy.get(event.getEntity()) instanceof Player){
				Player player = (Player) entityLastHitBy.get(event.getEntity());
				if (player.getEquipment().getItemInMainHand() != null){
					ItemStack item = player.getEquipment().getItemInMainHand();
					ItemWeapon weapon = new ItemWeapon(item);
					int Level = weapon.getLevel();
					int XP = weapon.getXP();
					if(Level >= 1){
						XP += Math.ceil(event.getEntity().getMaxHealth() / 500);
						if(XP >= Helper.calcMaxXP(Level)){
							XP = 0;
							Level += 1;
							weapon.setLevel(Level);
							player.sendMessage(Helper.colorCodes("&6Your weapon has reached &lLevel " + Level + "&6."));
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1);
						}
						weapon.setXP(XP);
						item = weapon.build();
						player.getEquipment().setItemInMainHand(item);
					}
				}
			}
			entityLastHitBy.remove(event.getEntity());
		}
	}
	
	@EventHandler
	private void onEntityHit(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity
		&& event.getDamager() instanceof LivingEntity){
			LivingEntity victim = (LivingEntity) event.getEntity();
			LivingEntity damager = (LivingEntity) event.getDamager();
			
			if(damager instanceof Player){
				Player dp = (Player) damager;
				PlayerData.get(dp).setInCombat(LocalDateTime.now());
			}
			
			if(victim instanceof Player){
				Player vp = (Player) victim;
				PlayerData.get(vp).setInCombat(LocalDateTime.now());
			}
			
//			if(!playerLastHitTick.containsKey((Player) victim)){
//				playerLastHitTick.put((Player) victim, System.currentTimeMillis());
//			}
//			if(victim.getType().equals(EntityType.PLAYER)){
//				Player player = (Player) victim;
//				if(!playerLastHitTick.containsKey((Player) victim)){
//					playerLastHitTick.put((Player) victim, System.currentTimeMillis());
//				} else if(System.currentTimeMillis() - playerLastHitTick.get(player) <= 2){
//					// This is probably impossible. Log them into anti-cheat system.
//					// TODO make anti-cheat system :/
//					event.setCancelled(true);
//					return;
//				}
//				playerLastHitTick.put(player, System.currentTimeMillis());
//			}
			entityLastHitBy.put(victim, damager);
			double dmg = (event.getDamage() >= 1) ? event.getDamage() : 1;
			double basedmg = dmg;
			
			// Implement custom damage from weapon's lore
			if(damager.getEquipment().getItemInMainHand() != null && damager.getEquipment().getItemInMainHand().getType() != org.bukkit.Material.AIR){
				ItemStack item = damager.getEquipment().getItemInMainHand();
				ItemWeapon weapon = new ItemWeapon(item);
				int MinDmg = weapon.getMinDmg();
				int MaxDmg = weapon.getMaxDmg();
				int FireDmg = weapon.getFireDmg();
				int IceDmg = weapon.getIceDmg();
				double LifeSteal = weapon.getLifeSteal();
				int Level = weapon.getLevel();
				
				// Normal damage
				if(MinDmg + MaxDmg > 0){
					if(MinDmg != MaxDmg) {
						Random random = new Random();
						dmg = random.nextInt(MaxDmg-MinDmg) + MinDmg;
					} else dmg = MaxDmg;
					if(Level >= 1){
						dmg += Helper.calcWepLevelBuff(MinDmg, Level-1);
					}
					basedmg = dmg;
				}
				
				// Fire damage
				if(FireDmg > 0){
					dmg += FireDmg;
					victim.setFireTicks(16);
				}
				
				// Ice damage
				if(IceDmg > 0){
					dmg += IceDmg;
					victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 1));
					victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
				}
				
				// Life steal
				if(LifeSteal > 0
						&& damager.getHealth() < damager.getMaxHealth()){
					double hp = damager.getHealth();
					double maxhp = damager.getMaxHealth();
					hp += (basedmg * LifeSteal);
					if(hp > maxhp){
						hp = maxhp;
					}
					damager.setHealth(hp);
					if(damager.getType().equals(EntityType.PLAYER)) Statistics.updateDisplay((Player) damager);
				}
				
			}
			
			// TODO Still a bit a work here to be done.
				
			// Apply custom damage
			event.setDamage(dmg);
			damager.setMaximumNoDamageTicks(5);
			victim.setMaximumNoDamageTicks(5);
	
			char shortenCause = event.getCause().name().charAt(0);
			
			new BukkitRunnable() {
				@Override
				public void run() {
					// Send combat info to damager
					if (damager.getType().equals(EntityType.PLAYER)) {
						Player dp = (Player) damager;
						if (victim.getType().equals(EntityType.PLAYER)) {
							Player vp = (Player) victim;
							dp.sendMessage(Helper.colorCodes(
									"        &c" + Math.round(event.getDamage()) + " &l>&f " + vp.getDisplayName() + "&7 (" + Math.round(vp.getHealth()) + " &lHP&7)&8 [&l" + shortenCause + "&8]"));
						} else if (victim.isCustomNameVisible()) {
							dp.sendMessage(Helper.colorCodes(
									"        &c" + Math.round(event.getDamage()) + " &l>&f " + victim.getCustomName() + "&7 (" + Math.round(victim.getHealth()) + " &lHP&7)&8 [&l" + shortenCause + "&8]"));
						} else {
							dp.sendMessage(Helper.colorCodes(
									"        &c" + Math.round(event.getDamage()) + " &l>&f " + victim.getName() + "&7 (" + Math.round(victim.getHealth()) + " &lHP&7)&8 [&l" + shortenCause + "&8]"));
						}
						Statistics.updateDisplay(dp);
					}
	
					// Send combat info to victim player
					if (victim.getType().equals(EntityType.PLAYER)) {
						Player vp = (Player) victim;
						vp.sendMessage(Helper.colorCodes(
								"        &6-" + Math.round(event.getDamage()) + " &l<&7 " + Math.round(victim.getHealth()) + " &lHP&8 [&l" + shortenCause + "&8]"));
						Statistics.updateDisplay(vp);
					}
				}
			}.runTaskLater(parent, 1);
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		new BukkitRunnable() {
			public void run() {
				Statistics.updateStats((LivingEntity) player);
			}
		}.runTaskLater(parent, 1);
	}

	@EventHandler
	private void onHealEvent(EntityRegainHealthEvent event) {
		if (event.getEntityType().equals(EntityType.PLAYER)) {
			Statistics.updateDisplay((Player) event.getEntity());
		}
	}

}
