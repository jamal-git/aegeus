package com.aegeus.game.ability;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.util.Action;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum Ability {
	TACKLE("Tackle", "The next attack deals bonus physical damage and slows the enemy.") {
		@Override
		public void activate(AgMonster info) {
			info.getOnHit().add(new Action<CombatInfo>() {
				@Override
				public void activate(CombatInfo cInfo) {
					cInfo.multPhysDmg(1.25f * (0.35 * info.getTier()));
					cInfo.addEffect(() -> cInfo.getTarget().addPotionEffect(
							new PotionEffect(PotionEffectType.SLOW, 40, 1)));
					cInfo.addSound(cInfo.getTarget().getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);
					info.setActiveAbil(null);
					CombatManager.updateName(info.getEntity());
				}
			});
		}
	},
	CONCUSS("Concuss", "The next attack blinds the enemy and immediately grants another ability.") {
		@Override
		public void activate(AgMonster info) {
			info.getOnHit().add(new Action<CombatInfo>() {
				@Override
				public void activate(CombatInfo cInfo) {
					cInfo.multMagDmg(1.35f * (0.45 * info.getTier()));
					cInfo.addEffect(() -> cInfo.getTarget().addPotionEffect(
							new PotionEffect(PotionEffectType.BLINDNESS, 40, 1)));
					cInfo.addSound(cInfo.getTarget().getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);
					info.setActiveAbil(null);
					CombatManager.updateName(info.getEntity());
				}
			});
		}
	},
	DETONATE("Detonate", "Creates an explosion that deals percentage health damage to nearby enemies.") {
		@Override
		public void activate(AgMonster info) {
			for (int i = 0; i < 5; i++) {
				int finalI = i;
				Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () -> {
					if (Aegeus.getInstance().getEntities().contains(info)) {
						LivingEntity entity = info.getEntity();
						int dist = Math.round(1 + (info.getTier() * 0.2f));
						entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CREEPER_HURT, 1, 1);
						entity.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, entity.getLocation(), 20 + (5 * info.getTier()), dist, dist, dist);

						if (finalI == 4) {
							entity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 60 + (20 * info.getTier()), dist, dist, dist);
							entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7f, 1);
							entity.getNearbyEntities(dist, dist, dist).stream().filter(e -> e instanceof Player)
									.map(e -> (Player) e)
									.forEach(e -> {
										e.damage(e.getHealth() * (0.05 + (0.05 * info.getTier())));
										Aegeus.getInstance().getPlayer(e).setAttacker(entity);
									});
							info.setActiveAbil(null);
							CombatManager.updateName(info.getEntity());
						}
					}
				}, i * 10);
			}
		}
	};

	private final String name;
	private final String desc;

	Ability(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public abstract void activate(AgMonster info);

	@Override
	public String toString() {
		return getName();
	}
}
