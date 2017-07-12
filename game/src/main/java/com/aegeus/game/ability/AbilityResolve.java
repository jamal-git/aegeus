package com.aegeus.game.ability;

import com.aegeus.game.Aegeus;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import org.bukkit.Bukkit;

public class AbilityResolve extends Ability {
	public AbilityResolve() {
		super("Resolve", "Physical and magic resist is increased for a short duration.");
	}

	@Override
	public void activate(AgMonster info) {
		float phys = (0.05f + info.getPhysRes()) * (1 + (0.3f * info.getTier()));
		float mag = (0.05f + info.getMagRes()) * (1 + (0.3f * info.getTier()));

		info.setPhysRes(info.getPhysRes() + phys);
		info.setMagRes(info.getMagRes() + mag);

		Bukkit.getScheduler().runTaskLater(Aegeus.getInstance(), () -> {
			if (Aegeus.getInstance().contains(info)) {
				info.setPhysRes(info.getPhysRes() - phys);
				info.setMagRes(info.getMagRes() - mag);
				info.setActiveAbil(null);
				CombatManager.updateName(info.getEntity());
			}
		}, 60);
	}
}
