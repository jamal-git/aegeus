package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgEntity;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.entity.AgProjectile;
import com.aegeus.game.item.ItemGold;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
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

			if (mInfo.getOrigin() != null) mInfo.getOrigin().decrementCount();

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

		if (info.getAttacker() instanceof Player) {
			Player player = (Player) info.getAttacker();
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
			Bukkit.getScheduler().runTaskLater(parent, () -> parent.removeEntity(entity), 5);

	}

	@EventHandler
	private void onChunkUnload(ChunkUnloadEvent e) {
		for (Entity entity : e.getChunk().getEntities()) {
			if (entity instanceof Projectile)
				parent.removeProjectile((Projectile) entity);
			else if (entity instanceof LivingEntity) {
				onDeath(new EntityDeathEvent((LivingEntity) entity, null));
				parent.removeEntity((LivingEntity) entity);
			}
		}
	}

	@EventHandler
	private void onDamage(EntityDamageEvent e) {
		EntityDamageByEntityEvent ee = e instanceof EntityDamageByEntityEvent
				? (EntityDamageByEntityEvent) e : null;
		Entity victim = e.getEntity();
		Entity damaged = e.getEntity();
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
			if (victim instanceof Player && attacker instanceof Player && Aegeus.getInstance().getPlayer((Player) victim).getParty() != null && Aegeus.getInstance().getPlayer((Player) victim).getParty().hasPlayer(Aegeus.getInstance().getPlayer((Player) attacker))) {
				e.setCancelled(true);
				return;
			}
			LivingEntity lVictim = (LivingEntity) victim;
			LivingEntity lAttacker = (LivingEntity) attacker;
			AgEntity vInfo = parent.getEntity(lVictim);
			AgEntity aInfo = parent.getEntity(lAttacker);

			e.setCancelled(true);
			if (attacker instanceof Player) {
				AgPlayer pInfo = parent.getPlayer((Player) attacker);
				CombatManager.takeEnergy((Player) attacker, tool);
				if (pInfo.getEnergy() <= 0) return;
			}

			int physDmg = 0;
			int magDmg = 0;
			int healing = 0;

			if (tool != null && !tool.getType().equals(Material.AIR) && new Weapon(tool).verify()) {
				if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
					if (tool.getType().equals(Material.BOW))
						return;
					if (Util.distance(victim.getLocation(), attacker.getLocation()) > 3.5)
						return;
				}

				Weapon weapon = new Weapon(tool);
				if (lAttacker instanceof Player) CombatManager.weaponDura((Player) lAttacker, weapon);
				if (lVictim instanceof Player) CombatManager.armorDura((Player) lVictim);

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
					physDmg += lVictim.getMaxHealth() * (0.01 * weapon.getTier());
					if (victim instanceof Player)
						victim.sendMessage(Util.colorCodes("            &c&l*** OPPONENT TRUE HEARTS&c!&l ***"));
					if (attacker instanceof Player)
						attacker.sendMessage(Util.colorCodes("            &e&l*** TRUE HEARTS&e!&l ***"));
				}
				if (weapon.getBlind() > 0 && random.nextFloat() <= weapon.getBlind()) {
					lVictim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 12 + (weapon.getTier() * 6), 1));
					if (victim instanceof Player)
						victim.sendMessage(Util.colorCodes("            &c&l*** OPPONENT BLINDNESS&c!&l ***"));
					if (attacker instanceof Player)
						attacker.sendMessage(Util.colorCodes("            &e&l*** BLINDNESS&e!&l ***"));
				}
				if (weapon.getLifeSteal() > 0) {
					healing += physDmg * weapon.getLifeSteal();
				}

				float critChance = aInfo.getCritChance() + (Util.isAxe(tool.getType()) ? 0.05f : 0);
				if (critChance > 0 && random.nextFloat() <= critChance) {
					physDmg *= 1.75;
					if (victim instanceof Player)
						victim.sendMessage(Util.colorCodes("            &c&l*** OPPONENT CRITICAL&c!&l ***"));
					if (attacker instanceof Player)
						attacker.sendMessage(Util.colorCodes("            &e&l*** CRITICAL&e!&l ***"));
				}

				physDmg *= 1 - Math.max(0, vInfo.getPhysRes() - weapon.getPen());
				magDmg *= 1 - Math.max(0, vInfo.getMagRes());

				if (healing > 0) Util.heal(lAttacker, healing);
			}

			if (vInfo.getReflect() > 0 && random.nextFloat() <= vInfo.getReflect()) {
				damaged = lAttacker;

				lVictim.getWorld().spawnParticle(Particle.BLOCK_DUST, lVictim.getLocation(),
						40, 0.25, 0.8, 0.25, 0.2, new MaterialData(Material.EMERALD_BLOCK));
				lVictim.getWorld().playSound(lVictim.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);

				if (attacker instanceof Player)
					attacker.sendMessage(Util.colorCodes("            &c&l*** OPPONENT REFLECTED&c!&l ***"));
				if (victim instanceof Player)
					victim.sendMessage(Util.colorCodes("            &e&l*** REFLECTED&e!&l ***"));
			} else if (vInfo.getDodge() > 0 && random.nextFloat() <= vInfo.getDodge()) {
				physDmg = 0;
				magDmg = 0;

				lVictim.getWorld().spawnParticle(Particle.BLOCK_DUST, lVictim.getLocation(),
						40, 0.25, 0.8, 0.25, 0.15, new MaterialData(Material.STONE));
				lVictim.getWorld().playSound(lVictim.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);

				if (attacker instanceof Player)
					attacker.sendMessage(Util.colorCodes("            &c&l*** OPPONENT DODGED&c!&l ***"));
				if (victim instanceof Player)
					victim.sendMessage(Util.colorCodes("            &e&l*** DODGED&e!&l ***"));
			} else if (vInfo.getBlock() > 0 && random.nextFloat() <= vInfo.getBlock()) {
				physDmg = 0;

				lVictim.getWorld().spawnParticle(Particle.BLOCK_DUST, lVictim.getLocation(),
						40, 0.25, 0.8, 0.25, 0.1, new MaterialData(Material.BEDROCK));
				lVictim.getWorld().playSound(lVictim.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1, 1);

				if (attacker instanceof Player)
					attacker.sendMessage(Util.colorCodes("            &c&l*** OPPONENT BLOCKED&c!&l ***"));
				if (victim instanceof Player)
					victim.sendMessage(Util.colorCodes("            &e&l*** BLOCKED&e!&l ***"));
			}

			e.setDamage(physDmg + magDmg);

			if (aInfo instanceof AgMonster)
				e.setDamage(e.getDamage() * ((AgMonster) aInfo).getDmgMultiplier());

			if (lAttacker instanceof Player && ((Player) lAttacker).isSneaking())
				e.setDamage(e.getDamage() / 2);
			e.setDamage(Math.max(1, e.getDamage()));

			if (e.getDamage() > 0 && damaged instanceof LivingEntity) {
				LivingEntity lDamaged = (LivingEntity) damaged;

				lDamaged.getWorld().spawnParticle(Particle.BLOCK_CRACK, lDamaged.getLocation(),
						110, 0.25, 0.8, 0.25, new MaterialData(Material.getMaterial(55)));
				lDamaged.damage(e.getDamage());
				lDamaged.setLastDamage(e.getDamage());
				lDamaged.setLastDamageCause(e);

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

		if (damaged instanceof LivingEntity) {
			LivingEntity lDamaged = (LivingEntity) damaged;

			lDamaged.setMaximumNoDamageTicks(3);
			lDamaged.setNoDamageTicks(lDamaged.getMaximumNoDamageTicks());
			lDamaged.setCustomNameVisible(true);

			if (lDamaged.getHealth() >= lDamaged.getMaxHealth() * 0.75) {
				lDamaged.setCustomName(Util.colorCodes("&7- &a" + Math.round(lDamaged.getHealth()) + " &lHP&7 -"));
			} else if (lDamaged.getHealth() >= lDamaged.getMaxHealth() * 0.50) {
				lDamaged.setCustomName(Util.colorCodes("&7- &e" + Math.round(lDamaged.getHealth()) + " &lHP&7 -"));
			} else if (lDamaged.getHealth() >= lDamaged.getMaxHealth() * 0.25) {
				lDamaged.setCustomName(Util.colorCodes("&7- &6" + Math.round(lDamaged.getHealth()) + " &lHP&7 -"));
			} else {
				lDamaged.setCustomName(Util.colorCodes("&7- &c" + Math.round(lDamaged.getHealth()) + " &lHP&7 -"));
			}
		}

		if (attacker instanceof LivingEntity) {
			LivingEntity lAttacker = (LivingEntity) attacker;
			AgEntity aInfo = parent.getEntity(lAttacker);
			aInfo.inCombat();
		}

		if (victim instanceof LivingEntity) {
			LivingEntity lVictim = (LivingEntity) victim;
			AgEntity vInfo = parent.getEntity(lVictim);
			vInfo.setAttacker(attacker);
			vInfo.inCombat();
		}

		if (damaged instanceof Player && e.getDamage() > 0) {
			Entity finalDamaged = damaged;
			Bukkit.getScheduler().runTaskLater(parent, () -> {
				Util.notifyAttacked((Player) finalDamaged, e.getDamage());
				Util.updateDisplay((Player) finalDamaged);
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
			AgPlayer info = parent.getPlayer(e.getPlayer());
			e.setCancelled(true);
			e.setUseItemInHand(Event.Result.ALLOW);

			if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
					&& e.getHand().equals(EquipmentSlot.HAND)) {
				if (info.getEnergy() > 0) e.getPlayer().launchProjectile(Arrow.class);
				else CombatManager.exhaust(e.getPlayer());
			}
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

	@EventHandler
	private void onShootBow(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player)
			e.setCancelled(true);
	}

}