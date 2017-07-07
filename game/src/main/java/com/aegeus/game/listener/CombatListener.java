package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.entity.AgProjectile;
import com.aegeus.game.item.ItemGold;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Condition;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CombatListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Aegeus parent;

	public CombatListener(Aegeus parent) {
		this.parent = parent;
	}

	@EventHandler
	private void onDeath(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		AgEntity info = parent.getEntity(entity);

		if (info instanceof AgMonster) {
			AgMonster mInfo = parent.getMonster(entity);
			if (random.nextFloat() <= mInfo.getChance()) {
				ItemStack mainHand = entity.getEquipment().getItemInMainHand();
				if (mainHand != null && !mainHand.getType().equals(Material.AIR) && random.nextFloat() <= 0.45f)
					entity.getWorld().dropItemNaturally(entity.getLocation(), mainHand);
				else if (entity.getEquipment().getArmorContents().length >= 1) {
					List<ItemStack> items = new ArrayList<>();
					Arrays.stream(entity.getEquipment().getArmorContents())
							.filter(i -> !i.getType().equals(Material.AIR))
							.forEach(items::add);
					entity.getWorld().dropItemNaturally(entity.getLocation(), items.get(random.nextInt(items.size())));
				}
			}
			if (mInfo.getGold() > 0 && random.nextFloat() <= mInfo.getGoldChance())
				entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemGold(mInfo.getGold()).build());
		}

		if (entity.getKiller() != null) {
			Player player = entity.getKiller();
			ItemStack tool = player.getInventory().getItemInMainHand();
			if (tool != null && !tool.getType().equals(Material.AIR) && new Weapon(tool).verify()) {
				Weapon weapon = new Weapon(tool);
				int xp = 45 + ((int) Math.round(entity.getMaxHealth() / 300));
				weapon.addXp(xp);
				if (weapon.getXp() >= weapon.getMaxXp()) {
					weapon.addLevel(1);
					weapon.setXp(0);
					player.sendMessage(Util.colorCodes("&6Your weapon has reached &lLevel " + (weapon.getLevel() + 1) + "&6."));
				}
				player.getInventory().setItemInMainHand(weapon.build());
			}
		}

		// Clear entity's data if not player
		if (!(entity instanceof Player))
			Bukkit.getScheduler().runTaskLater(parent, () -> parent.removeEntity(entity), 2);

	}

	@EventHandler
	private void onDamage(EntityDamageEvent e) {
		EntityDamageByEntityEvent ee = e instanceof EntityDamageByEntityEvent
				? (EntityDamageByEntityEvent) e : null;
		Entity victim = e.getEntity();
		Entity attacker = ee != null ? ee.getDamager() : null;
		ItemStack tool = attacker instanceof LivingEntity ?
				((LivingEntity) attacker).getEquipment().getItemInMainHand() : null;

		if (attacker instanceof Projectile && ((Projectile) attacker).getShooter() instanceof Entity) {
			AgProjectile p = parent.getProjectile((Projectile) ee.getDamager());
			attacker = (Entity) p.getProjectile().getShooter();
			tool = p.getFiredWith();
		}

		if (victim instanceof LivingEntity && attacker instanceof LivingEntity
				&& !victim.isDead() && !attacker.isDead()) {
			LivingEntity lVictim = (LivingEntity) victim;
			LivingEntity lAttacker = (LivingEntity) attacker;
			AgEntity vInfo = parent.getEntity(lVictim);
			AgEntity aInfo = parent.getEntity(lAttacker);

			e.setCancelled(true);
			if (aInfo instanceof AgPlayer) {
				AgPlayer pInfo = (AgPlayer) aInfo;
				if (pInfo.getEnergy() > 0) {
					pInfo.setEnergy(pInfo.getEnergy() - 9);
					Util.updateDisplay(pInfo.getPlayer());
				}
				if (pInfo.getEnergy() <= 0) {
					pInfo.setEnergy(-40);
					lAttacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 4));
					Util.updateDisplay(pInfo.getPlayer());
					return;
				}
				Util.updateDisplay(pInfo.getPlayer());
			}

			int physDmg = 0;
			int magDmg = 0;
			int healing = 0;

			if (tool != null && !tool.getType().equals(Material.AIR) && new Weapon(tool).verify()) {
				tool.setDurability((short) 0);
				if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
					if (tool.getType().equals(Material.BOW))
						return;
					if (Util.distance(victim.getLocation(), attacker.getLocation()) > 3.5)
						return;
				}


				Weapon weapon = new Weapon(tool);
				physDmg = weapon.getDmg();

				if (weapon.getFireDmg() > 0) {
					magDmg += weapon.getFireDmg();
					lVictim.setFireTicks(38 + (weapon.getTier() * 7));
				}
				if (weapon.getIceDmg() > 0) {
					magDmg += weapon.getIceDmg();
					lVictim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 + (weapon.getTier() * 4), 2));
					lVictim.getWorld().playSound(lVictim.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
				}
				if (weapon.getPoisonDmg() > 0) {
					magDmg += weapon.getPoisonDmg();
					lVictim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30 + (weapon.getTier() * 12), 1));
					lVictim.getWorld().playSound(lVictim.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
				}
				if (weapon.getPureDmg() > 0) {
					int matches = (int) Arrays.stream(lVictim.getEquipment().getArmorContents())
							.filter(i -> i != null && !i.getType().equals(Material.AIR) && new Armor(i).verify())
							.map(Armor::new).count();
					physDmg += weapon.getPureDmg() * (matches / 4);
				}
				if (weapon.getTrueHearts() > 0 && random.nextFloat() <= weapon.getTrueHearts()) {
					physDmg += lVictim.getMaxHealth() * (0.005 * weapon.getTier());
					if (victim instanceof Player)
						victim.sendMessage(Util.colorCodes("              &c&l*** OPPONENT TRUE HEARTS&c!&l ***"));
					if (attacker instanceof Player)
						attacker.sendMessage(Util.colorCodes("              &e&l*** TRUE HEARTS&e!&l ***"));
				}
				if (weapon.getBlind() > 0 && random.nextFloat() <= weapon.getBlind()) {
					lVictim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 12 + (weapon.getTier() * 6), 1));
					if (victim instanceof Player)
						victim.sendMessage(Util.colorCodes("              &c&l*** OPPONENT BLINDNESS&c!&l ***"));
					if (attacker instanceof Player)
						attacker.sendMessage(Util.colorCodes("              &e&l*** BLINDNESS&e!&l ***"));
				}
				if (weapon.getLifeSteal() > 0) {
					healing += physDmg * weapon.getLifeSteal();
				}

				float critChance = aInfo.getCritChance() + (Util.isAxe(tool.getType()) ? 0.05f : 0);
				if (critChance > 0 && random.nextFloat() <= critChance) {
					physDmg *= 1.25;
					if (victim instanceof Player)
						victim.sendMessage(Util.colorCodes("              &c&l*** OPPONENT CRITICAL&c!&l ***"));
					if (attacker instanceof Player)
						attacker.sendMessage(Util.colorCodes("              &e&l*** CRITICAL&e!&l ***"));
				}

				physDmg *= 1 - Math.max(0, vInfo.getPhysRes() - weapon.getPen());
				magDmg *= 1 - Math.max(0, vInfo.getMagRes());

				if (healing > 0) Util.heal(lAttacker, healing);
			}

			LivingEntity damaged = lVictim;

			if (vInfo.getBlock() > 0 && random.nextFloat() <= vInfo.getBlock()) {
				physDmg = 0;
				lVictim.getWorld().playSound(lVictim.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);
				if (attacker instanceof Player)
					attacker.sendMessage(Util.colorCodes("              &c&l*** OPPONENT BLOCKED&c!&l ***"));
				if (victim instanceof Player)
					victim.sendMessage(Util.colorCodes("              &a&l*** BLOCKED&e!&l ***"));
			} else if (vInfo.getDodge() > 0 && random.nextFloat() <= vInfo.getDodge()) {
				physDmg = 0;
				magDmg = 0;
				lVictim.getWorld().playSound(lVictim.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 1, 1);
				if (attacker instanceof Player)
					attacker.sendMessage(Util.colorCodes("              &c&l*** OPPONENT DODGED&c!&l ***"));
				if (victim instanceof Player)
					victim.sendMessage(Util.colorCodes("              &a&l*** DODGED&e!&l ***"));
			} else if (vInfo.getReflect() > 0 && random.nextFloat() <= vInfo.getReflect()) {
				damaged = lAttacker;
				lVictim.getWorld().playSound(lVictim.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 1, 1);
				if (attacker instanceof Player)
					attacker.sendMessage(Util.colorCodes("              &c&l*** OPPONENT REFLECTED&c!&l ***"));
				if (victim instanceof Player)
					victim.sendMessage(Util.colorCodes("              &a&l*** REFLECTED&e!&l ***"));
			}

			e.setDamage(physDmg + magDmg);

			if (aInfo instanceof AgMonster)
				e.setDamage(e.getDamage() * ((AgMonster) aInfo).getDmgMultiplier());
			if (vInfo instanceof AgMonster) {
				AgMonster mInfo = (AgMonster) vInfo;
				for (int i = mInfo.getHitConds().size() - 1; i >= 0; i--) {
					Condition<LivingEntity> c = mInfo.getHitConds().get(i);
					if (c.isComplete(lVictim)) {
						c.onComplete(lVictim);
						if (c.addOnComplete() != null)
							mInfo.getHitConds().addAll(c.addOnComplete());
						if (c.removeOnComplete()) {
							mInfo.getHitConds().remove(c);
							i--;
						}
					}
				}

			}

			if (lAttacker instanceof Player && ((Player) lAttacker).isSneaking())
				e.setDamage(e.getDamage() / 2);
			e.setDamage(Math.max(1, e.getDamage()));

			if (e.getDamage() > 0) {
				damaged.damage(e.getDamage());
				damaged.setLastDamage(e.getDamage());
				damaged.setLastDamageCause(e);

				if (damaged.equals(lVictim)) {
					float multiply = 0.04f;
					if (((damaged instanceof Player && !((Player) damaged).isSneaking())
							|| !(victim instanceof Player)) && damaged.isOnGround())
						multiply += 0.17;
					Vector vec = lAttacker.getLocation().getDirection().multiply(multiply);
					damaged.setVelocity(vec.setY(vec.getY() + 0.08));
				}
			}
		}

		if (victim instanceof LivingEntity) {
			LivingEntity lVictim = (LivingEntity) victim;
			AgEntity vInfo = parent.getEntity(lVictim);
			vInfo.inCombat();

			lVictim.setMaximumNoDamageTicks(3);
			lVictim.setNoDamageTicks(lVictim.getMaximumNoDamageTicks());
			lVictim.setCustomNameVisible(true);

			if (lVictim.getHealth() >= lVictim.getMaxHealth() * 0.75) {
				lVictim.setCustomName(Util.colorCodes("&7- &a" + Math.round(lVictim.getHealth()) + " &lHP&7 -"));
			} else if (lVictim.getHealth() >= lVictim.getMaxHealth() * 0.50) {
				lVictim.setCustomName(Util.colorCodes("&7- &e" + Math.round(lVictim.getHealth()) + " &lHP&7 -"));
			} else if (lVictim.getHealth() >= lVictim.getMaxHealth() * 0.25) {
				lVictim.setCustomName(Util.colorCodes("&7- &6" + Math.round(lVictim.getHealth()) + " &lHP&7 -"));
			} else {
				lVictim.setCustomName(Util.colorCodes("&7- &c" + Math.round(lVictim.getHealth()) + " &lHP&7 -"));
			}
		}

		if (attacker instanceof LivingEntity) {
			LivingEntity lAttacker = (LivingEntity) attacker;
			AgEntity aInfo = parent.getEntity(lAttacker);
			aInfo.inCombat();
		}

		if (victim instanceof Player && e.getDamage() > 0) {
			Bukkit.getScheduler().runTaskLater(parent, () -> {
				Util.notifyAttacked((Player) victim, e.getDamage());
				Util.updateDisplay((Player) victim);
			}, 1);
		}
		if (attacker instanceof Player && victim instanceof LivingEntity && e.getDamage() > 0) {
			Entity finalAttacker = attacker;
			Bukkit.getScheduler().runTaskLater(parent, () -> {
				Util.notifyAttack((Player) finalAttacker, (LivingEntity) victim, e.getDamage());
				Util.updateDisplay((Player) finalAttacker);
			}, 1);
		}
	}

	@EventHandler
	private void onInteract(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().getType().equals(Material.BOW)) {
			e.setCancelled(true);
			e.setUseItemInHand(Event.Result.ALLOW);

			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
					&& e.getHand().equals(EquipmentSlot.HAND))
				e.getPlayer().launchProjectile(Arrow.class);
		}
	}

	@EventHandler
	private void onProjectileHit(ProjectileHitEvent e) {
		Bukkit.getScheduler().runTaskLater(parent, () -> {
			parent.removeProjectile(e.getEntity());
			e.getEntity().remove();
		}, 1);
	}

	@EventHandler
	private void onPickupArrow(PlayerPickupArrowEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	private void onShoot(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) e.getEntity().getShooter();
			AgProjectile p = parent.getProjectile(e.getEntity());
			p.setFiredWith(entity.getEquipment().getItemInMainHand());
		}
	}

}
