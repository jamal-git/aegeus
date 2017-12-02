package com.aegeus.game.ability;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.stream.IntStream;

/**
 * Detonate
 *
 * Creates an explosion that deals true damage to nearby enemies.
 */
public class AbilityDetonate extends Ability {
	private int power = 0;

	public AbilityDetonate() {
		this(-1);
	}

	public AbilityDetonate(int power) {
		super("Detonate", "Creates an explosion that deals true damage to nearby enemies.");
		this.power = power;
	}

	@Override
	public void activate(AgMonster info) {
		int power = this.power == -1 ? info.getTier() : this.power;
		IntStream.range(0, 5).forEach(i -> Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () -> {
			if (Aegeus.getInstance().contains(info)) {
				LivingEntity entity = info.getEntity();
				int dist = Math.round(1 + (power * 0.2f));
				entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CREEPER_HURT, 1, 1);
				entity.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, entity.getLocation(), 30 + (10 * power), dist, dist, dist);

				if (i == 4) {
					entity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 60 + (20 * power), dist, dist, dist);
					entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7f, 1);
					entity.getNearbyEntities(dist, dist, dist).stream().filter(e -> e instanceof Player)
							.map(e -> (Player) e)
							.forEach(e -> {
								e.damage(e.getHealth() * (0.05 + (0.05 * power)));
								Aegeus.getInstance().getPlayer(e).setAttacker(entity);
							});
					CombatManager.updateName(entity);
					info.setActiveAbil(null);
				}
			}
		}, i * 10));
	}
}
