package com.aegeus.game.combat;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombatManager {
	public static void takeEnergy(Player player, ItemStack tool) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		Weapon weapon = tool != null && new Weapon(tool).verify() ? new Weapon(tool) : null;
		float energy = weapon != null ? 6.5f + (3.5f * (weapon.getTier() - 1)) : 6;

		info.setEnergy(info.getEnergy() - energy);

		if (info.getEnergy() <= 0) {
			info.setEnergy(-40);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 55, 4));
			return;
		}

		Util.updateDisplay(info.getPlayer());
	}
}
