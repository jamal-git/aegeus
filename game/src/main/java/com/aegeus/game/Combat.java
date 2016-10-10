package com.aegeus.game;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.aegeus.game.item.ItemWeapon;
import com.aegeus.game.planets.Planet;
import com.aegeus.game.player.EntityData;
import com.aegeus.game.util.Helper;

public class Combat implements Listener {

	private JavaPlugin parent;

	public Combat(JavaPlugin parent) {
		this.parent = parent;
	}

	@EventHandler
	private void onEntityDeath(EntityDeathEvent e){
		LivingEntity entity = e.getEntity();
		EntityData data = EntityData.get(entity);
		
		if (data.getLastHitBy() != null){
			LivingEntity lastHitBy = data.getLastHitBy();
			if(lastHitBy instanceof Player){
				Player player = (Player) lastHitBy;
				if (player.getEquipment().getItemInMainHand() != null
						&& !player.getEquipment().getItemInMainHand().getType().equals(Material.AIR)){
					ItemWeapon weapon = new ItemWeapon(player.getEquipment().getItemInMainHand());
					int level = weapon.getLevel();
					int xp = weapon.getXp();
					xp += (entity.getMaxHealth() / 500);
					if(xp > Helper.calcMaxXP(level)) {
						xp = 0;
						level += 1;
					}
					weapon.setLevel(level);
					weapon.setXp(xp);
					player.getEquipment().setItemInMainHand(weapon.build());
				}
			}
		}
		
		// Clear entity's data if not player
		if(!(entity instanceof Player))
			EntityData.remove(entity);
		
	}
	
	@EventHandler
	private void onHit(EntityDamageEvent e) {
		EntityDamageByEntityEvent ee = null;
		if(e instanceof EntityDamageByEntityEvent) // Created DamageByEntity event incase needed
			ee = (EntityDamageByEntityEvent) e;
		
		if(ee != null && ee.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) ee.getDamager();
			LivingEntity entity = (LivingEntity) e.getEntity();
			if(damager.getEquipment().getItemInMainHand() != null
					&& damager.getEquipment().getItemInMainHand().getItemMeta() != null) {
				ItemWeapon weapon = new ItemWeapon(damager.getEquipment().getItemInMainHand());
				if(weapon.getMinDmg() > 0 || weapon.getMaxDmg() > 0)
					if(weapon.getMaxDmg() > weapon.getMinDmg()) {
						Random random = new Random();
						e.setDamage(random.nextInt(weapon.getMaxDmg() - weapon.getMinDmg()) + weapon.getMinDmg());
					} else
						e.setDamage(weapon.getMinDmg());
				if(weapon.getIceDmg() > 0) {
					e.setDamage(e.getDamage() + (weapon.getIceDmg() * 0.1));
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 + (3 * weapon.getTier()), 2));
				}
				if(weapon.getFireDmg() > 0) {
					e.setDamage(e.getDamage() + (weapon.getFireDmg() * 0.2));
					entity.setFireTicks(16 + (3 * weapon.getTier()));
				}
				if(weapon.getLifeSteal() > 0) {
					double hp = damager.getHealth();
					hp += (e.getDamage() * weapon.getLifeSteal());
					damager.setHealth((hp > damager.getHealth()) ? damager.getHealth() : hp);
				}
			}
		}
		
		if(e.getDamage() < 1.0f) e.setDamage(1.0f); // Set minimum damage to 1
		
		switch(e.getCause()) {
			case PROJECTILE:
				// TODO Discuss this as this may not be final
				ee.getDamager().remove();
				break;
			case VOID:
				// TODO When alignments are done, only teleport player to the terminal if lawful
				if(e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					p.teleport(Planet.Enum.TERMINAL.getPlanet().getLocation());
				}
				break;
			case ENTITY_ATTACK:
				parent.getLogger().info("entity");
				if(e.getEntity() instanceof Player) {
					Player player = (Player) e.getEntity();
					notifyAttacked(player, e.getDamage());
				}
				if(e.getEntity() instanceof LivingEntity
						&& ee.getDamager() instanceof Player) {
					LivingEntity victim = (LivingEntity) e.getEntity();
					Player attacker = (Player) ee.getDamager();
					notifyAttack(attacker, victim, e.getDamage());
				}
				break;
			default:
				parent.getLogger().info("default");
				if(e.getEntity() instanceof Player) {
					Player player = (Player) e.getEntity();
					notifyAttacked(player, e.getDamage());
				}
		}
	}
	
	public void notifyAttack(Player player, LivingEntity attacked, double damage) {
		String name = (attacked.getCustomName() != null) ? attacked.getCustomName() : attacked.getName();
		long hp = Math.round(Math.ceil(attacked.getHealth()));
		long maxHp = Math.round(Math.ceil(attacked.getMaxHealth()));
		long dmg = Math.round(Math.ceil(damage));
		player.sendMessage(Helper.colorCodes("          &e" + dmg + " &l>&f " + name + "&7 [" + hp + " / " + maxHp + "]"));
	}
	
	public void notifyAttacked(Player player, double damage) {
		long hp = Math.round(Math.ceil(player.getHealth()));
		long maxHp = Math.round(Math.ceil(player.getMaxHealth()));
		long dmg = Math.round(Math.ceil(damage));
		player.sendMessage(Helper.colorCodes("            &c-" + dmg + "&7 [" + hp + " / " + maxHp + "]"));
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		new BukkitRunnable() {
			public void run() { Statistics.updateStats(player); }
		}.runTaskLater(parent, 1);
	}

	@EventHandler
	private void onHealEvent(EntityRegainHealthEvent event) {
		if (event.getEntityType().equals(EntityType.PLAYER)) {
			Statistics.updateDisplay((Player) event.getEntity());
		}
	}

}
