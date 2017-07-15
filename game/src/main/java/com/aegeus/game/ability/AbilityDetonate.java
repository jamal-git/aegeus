package com.aegeus.game.ability;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AbilityDetonate extends Ability {
	public AbilityDetonate() {
		super("Detonate", "Creates an explosion that deals magic damage to nearby enemies.");
	}

	@Override
	public void activate(AgMonster info) {
		for (int i = 0; i < 5; i++) {
			int finalI = i;
			Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () -> {
				if (Aegeus.getInstance().contains(info)) {
					LivingEntity entity = info.getEntity();
					int dist = Math.round(1 + (info.getTier().getLevel() * 0.2f));
					entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CREEPER_HURT, 1, 1);
					entity.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, entity.getLocation(), 30 + (10 * info.getTier().getLevel()), dist, dist, dist);

					if (finalI == 4) {
						entity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 60 + (20 * info.getTier().getLevel()), dist, dist, dist);
						entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7f, 1);
						entity.getNearbyEntities(dist, dist, dist).stream().filter(e -> e instanceof Player)
								.map(e -> (Player) e)
								.forEach(e -> {
									e.damage(e.getHealth() * (0.05 + (0.05 * info.getTier().getLevel())));
									Aegeus.getInstance().getPlayer(e).setAttacker(entity);
								});
						info.setActiveAbil(null);
						CombatManager.updateName(info.getEntity());
					}
				}
			}, i * 10);
		}
	}
}
