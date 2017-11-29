package com.aegeus.game.ability;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

public class AbilityResolve extends Ability {
	private int power = -1;

	public AbilityResolve() {
		this(-1);
	}

	public AbilityResolve(int power) {
		super("Resolve", "Grants bonus physical and magic resist for a short period of time.");
		this.power = power;
	}

	@Override
	public void activate(AgMonster info) {
		int power = this.power == -1 ? info.getTier() : this.power;

		LivingEntity entity = info.getEntity();
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);

		float phys = info.getPhysRes() * (power * 0.2f);
		float mag = info.getMagRes() * (power * 0.2f);
		info.setPhysRes(info.getPhysRes() + phys);
		info.setMagRes(info.getMagRes() + mag);

		Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () -> {
			if (Aegeus.getInstance().contains(info)) {
				entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITHER_HURT, 2f, 0.7f);
				info.setPhysRes(info.getPhysRes() - phys);
				info.setMagRes(info.getMagRes() - mag);
				info.setActiveAbil(null);
				CombatManager.updateName(entity);
			}
		}, 20 + (10 * power));
	}
}
