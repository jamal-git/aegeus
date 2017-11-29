package com.aegeus.game.ability;

import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.util.Action;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityTackle extends Ability {
	private int power = 0;

	public AbilityTackle() {
		this(-1);
	}

	public AbilityTackle(int power) {
		super("Tackle", "The next attack deals bonus physical damage and blinds the enemy.");
		this.power = power;
	}

	@Override
	public void activate(AgMonster info) {
		int power = this.power == -1 ? info.getTier() : this.power;
		info.getOnHit().add(new Action<CombatInfo>() {
			@Override
			public void activate(CombatInfo cInfo) {
				cInfo.addPhysDmg(10 + (power * 40));
				cInfo.addEffect(() -> cInfo.getTarget().addPotionEffect(
						new PotionEffect(PotionEffectType.BLINDNESS, 40, 1)));
				cInfo.addSound(cInfo.getTarget().getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);
				info.setActiveAbil(null);
			}
		});
	}
}
