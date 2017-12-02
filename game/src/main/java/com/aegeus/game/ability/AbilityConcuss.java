package com.aegeus.game.ability;

import com.aegeus.game.combat.CombatInfo;
import com.aegeus.game.entity.AgMonster;
import com.aegeus.game.util.Action;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Concuss
 *
 * The next attack deals bonus magic damage and blinds the enemy.
 */
public class AbilityConcuss extends Ability {
	private int power = -1;

	public AbilityConcuss() {
		this(-1);
	}

	public AbilityConcuss(int power) {
		super("Concuss", "The next attack deals bonus magic damage and blinds the enemy.");
		this.power = power;
	}

	@Override
	public void activate(AgMonster info) {
		int power = this.power == -1 ? info.getTier() : this.power;
		info.getOnHit().add(new Action<CombatInfo>() {
			@Override
			public void activate(CombatInfo cInfo) {
				cInfo.addMagDmg(5 + (power * 20));
				cInfo.addEffect(() -> cInfo.getTarget().addPotionEffect(
						new PotionEffect(PotionEffectType.BLINDNESS, 40, 1)));
				cInfo.addSound(cInfo.getTarget().getLocation(), Sound.ENTITY_WITHER_HURT, 0.7f, 0.7f);
				info.setActiveAbil(null);
			}
		});
	}
}
