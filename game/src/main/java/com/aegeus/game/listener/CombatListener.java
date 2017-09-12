package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.entity.AgProjectile;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.item.Items;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Action;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.IntPoss;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CombatListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Aegeus parent;

	public CombatListener(Aegeus parent) {
		this.parent = parent;
	}

	@EventHandler
	private void onDeath(EntityDeathEvent e) {
	    e.getDrops().clear();
		LivingEntity entity = e.getEntity();
		AgLiving info = parent.getLiving(entity);

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
				entity.getWorld().dropItemNaturally(entity.getLocation(), Items.getGold(mInfo.getGold()));
			for(EnumCraftingMaterial m : mInfo.getDrops().keySet()) {
			    Map<EnumCraftingMaterial, Chance<IntPoss>> drops = mInfo.getDrops();
                IntPoss chance = null;
			    if((chance = drops.get(m).get()) != null) {
			        ItemStack stack = m.getItem();
			        stack.setAmount(chance.get());
			        entity.getWorld().dropItemNaturally(entity.getLocation(), stack);
			        //todo implement hologram above the item
                }
            }
		}

		if (info.getAttacker() instanceof Player) {
			Player player = (Player) info.getAttacker();
			ItemStack tool = player.getInventory().getItemInMainHand();
			if (tool != null && !tool.getType().equals(Material.AIR) && Weapon.hasWeaponInfo(tool)) {
				Weapon weapon = new Weapon(tool);
				int xp = CombatManager.getXpGain(entity);
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
			Bukkit.getScheduler().runTaskLater(parent, () -> parent.remove(entity), 5);

	}

	@EventHandler
	private void onChunkUnload(ChunkUnloadEvent e) {
		for (Entity entity : e.getChunk().getEntities()) {
			if (!(entity instanceof Player) && parent.contains(entity)) {
				parent.remove(entity);
				entity.remove();
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
			e.setCancelled(true);

			if (victim instanceof Player && attacker instanceof Player) {
				AgPlayer vPInfo = parent.getPlayer((Player) victim);
				AgPlayer vAInfo = parent.getPlayer((Player) attacker);

				if (vPInfo.isInParty() && vAInfo.isInParty()
						&& vPInfo.getParty().equals(vAInfo.getParty()))
					return;
			}


			LivingEntity lVictim = (LivingEntity) victim;
			LivingEntity lAttacker = (LivingEntity) attacker;
			AgLiving vInfo = parent.getLiving(lVictim);
			AgLiving aInfo = parent.getLiving(lAttacker);

			e.setCancelled(true);
			if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
				if (tool != null && tool.getType().equals(Material.BOW))
					return;
				if (victim.getLocation().distance(attacker.getLocation()) > 3.5)
					return;
			}

			CombatInfo cInfo = CombatManager.get(lVictim, lAttacker, tool);

			if (aInfo instanceof AgMonster) {
				AgMonster mInfo = (AgMonster) aInfo;
				e.setDamage(e.getDamage() * ((AgMonster) aInfo).getDmgMultiplier());
				mInfo.setOnHit(Action.loop(mInfo.getOnHit(), cInfo));
			}

			if (vInfo instanceof AgMonster) {
				AgMonster mInfo = (AgMonster) vInfo;
				mInfo.setOnDamaged(Action.loop(mInfo.getOnDamaged(), cInfo));
				if (random.nextFloat() <= mInfo.getAbilChance()) CombatManager.useAbility(mInfo);
			}

			cInfo.getEffects().forEach(Runnable::run);
			cInfo.getSounds().forEach(s -> s.loc.getWorld().playSound(s.loc, s.sound, s.vol, s.pitch));

			LivingEntity lDamaged = cInfo.getTarget();
			e.setDamage(cInfo.getPhysDmg() + cInfo.getMagDmg());

			if (lAttacker instanceof Player && ((Player) lAttacker).isSneaking())
				e.setDamage(e.getDamage() / 2);
			e.setDamage(Math.max(1, e.getDamage()));

			if (e.getDamage() > 0) {
				lDamaged.getWorld().spawnParticle(Particle.BLOCK_CRACK, lDamaged.getLocation(),
						110, 0.25, 0.8, 0.25, new MaterialData(Material.getMaterial(55)));
				lDamaged.damage(e.getDamage());
				lDamaged.setLastDamage(e.getDamage());
				lDamaged.setLastDamageCause(e);

				if (damaged.equals(lVictim) && cInfo.getKnockback() > 0) {
					Vector vec = lAttacker.getLocation().getDirection().multiply(cInfo.getKnockback());
					if (((damaged instanceof Player && !((Player) damaged).isSneaking())
							|| !(victim instanceof Player)) && damaged.isOnGround())
						vec.setY(damaged.getVelocity().getY() + 0.3);
					damaged.setVelocity(vec);
				}
			}
		}

		if (damaged instanceof LivingEntity) {
			LivingEntity lDamaged = (LivingEntity) damaged;

			lDamaged.setMaximumNoDamageTicks(3);
			lDamaged.setNoDamageTicks(lDamaged.getMaximumNoDamageTicks());
			lDamaged.setCustomNameVisible(true);

			if (!(lDamaged instanceof Player))
				CombatManager.updateName(lDamaged);
		}

		if (attacker instanceof LivingEntity) {
			LivingEntity lAttacker = (LivingEntity) attacker;
			AgLiving aInfo = parent.getLiving(lAttacker);
			aInfo.inCombat();
		}

		if (victim instanceof LivingEntity) {
			LivingEntity lVictim = (LivingEntity) victim;
			AgLiving vInfo = parent.getLiving(lVictim);
			vInfo.setAttacker(attacker);
			vInfo.inCombat();
		}

		if (damaged instanceof Player && e.getDamage() > 0) {
			Bukkit.getScheduler().runTaskLater(parent, () -> {
				Util.notifyAttacked((Player) damaged, e.getDamage());
				Util.updateDisplay((Player) damaged);
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

			if ((e.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_AIR)
					|| e.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK))
					&& e.getHand().equals(EquipmentSlot.HAND)) {
				e.getPlayer().launchProjectile(Arrow.class);
			}
		}
	}

	@EventHandler
	private void onProjectileHit(ProjectileHitEvent e) {
		Bukkit.getScheduler().runTaskLater(parent, () -> {
			parent.remove(e.getEntity());
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