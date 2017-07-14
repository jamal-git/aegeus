package com.aegeus.game.ability;

import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.util.Action;
import org.bukkit.Sound;

public class AbilityTackle extends Ability {
	public AbilityTackle() {
		super("Tackle", "The next attack deals bonus physical damage.");
	}

	@Override
	public void activate(AgMonster info) {
		info.getOnHit().add(new Action<CombatInfo>() {
			@Override
			public void activate(CombatInfo cInfo) {
				cInfo.multPhysDmg(1.25f * (0.35 * info.getTier().getLevel()));
				cInfo.addSound(cInfo.getTarget().getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);
				info.setActiveAbil(null);
				CombatManager.updateName(info.getEntity());
			}
		});
	}
}
