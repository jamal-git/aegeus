package com.aegeus.game.ability;

import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.combat.CombatManager;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.util.Action;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityConcuss extends Ability {
	public AbilityConcuss() {
		super("Concuss", "The next attack blinds the enemy and immediately grants another ability.");
	}

	@Override
	public void activate(AgMonster info) {
		info.getOnHit().add(new Action<CombatInfo>() {
			@Override
			public void activate(CombatInfo cInfo) {
				cInfo.multMagDmg(1.35f * (0.45 * info.getTier().getLevel()));
				cInfo.addEffect(() -> cInfo.getTarget().addPotionEffect(
						new PotionEffect(PotionEffectType.BLINDNESS, 40, 1)));
				cInfo.addSound(cInfo.getTarget().getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);
				info.setActiveAbil(null);
				CombatManager.updateName(info.getEntity());
			}
		});
	}
}
