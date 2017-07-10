package com.aegeus.game.combat;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombatManager {
	public static void takeEnergy(Player player, ItemStack tool) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		Weapon weapon = tool != null && !tool.getType().equals(Material.AIR)
				&& new Weapon(tool).verify() ? new Weapon(tool) : null;
		float energy = weapon != null ? 6.5f + (3.5f * (weapon.getTier() - 1)) : 6;

		info.setEnergy(info.getEnergy() - energy);

		if (info.getEnergy() <= 0) exhaust(player);

		Util.updateDisplay(info.getPlayer());
	}

	public static void exhaust(Player player) {
		AgPlayer info = Aegeus.getInstance().getPlayer(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 55, 4));
		info.setEnergy(-40);
	}

	public static void weaponDura(Player player, Weapon weapon) {
		if (weapon.getMaxDura() > 0) {
			for (int i = 0; i < player.getInventory().getSize(); i++) {
				ItemStack item = player.getInventory().getItem(i);
				if (item != null && new Weapon(item).getTime().equals(weapon.getTime())) {
					weapon.subtractDura(1);
					if (weapon.getDura() <= 0)
						player.getInventory().setItem(i, new ItemStack(Material.AIR));
					else
						player.getInventory().setItem(i, weapon.build());
				}
			}
		}
	}

	public static void armorDura(Player player) {
		player.getInventory().setHelmet(armorDura(player.getInventory().getHelmet()));
		player.getInventory().setChestplate(armorDura(player.getInventory().getChestplate()));
		player.getInventory().setLeggings(armorDura(player.getInventory().getLeggings()));
		player.getInventory().setBoots(armorDura(player.getInventory().getBoots()));
	}

	public static ItemStack armorDura(ItemStack item) {
		if (item != null && new Armor(item).verify()) {
			Armor armor = new Armor(item);
			if (armor.getMaxDura() > 0) {
				armor.subtractDura(1);
				if (armor.getDura() <= 0)
					return new ItemStack(Material.AIR);
			}
			return armor.build();
		}

		return item;
	}
}
