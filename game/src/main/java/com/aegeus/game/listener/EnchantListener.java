package com.aegeus.game.listener;

import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.ArmorEnchant;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.item.tool.WeaponEnchant;
import com.aegeus.game.util.Util;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.concurrent.ThreadLocalRandom;

public class EnchantListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@EventHandler
	private void onInteract(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().getType().equals(Material.EMPTY_MAP))
			e.setCancelled(true);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		ItemStack cursor = e.getCursor();

		if (e.isLeftClick() && item != null && !item.getType().equals(Material.AIR)
				&& cursor != null && !cursor.getType().equals(Material.AIR)) {
			if (new Weapon(item).verify() && new WeaponEnchant(cursor).verify()) {
				Weapon weapon = new Weapon(item);
				WeaponEnchant enchant = new WeaponEnchant(cursor);
				if (weapon.getTier() == enchant.getTier()) {
					e.setCancelled(true);
					e.setCursor(new ItemStack(Material.AIR));
					if (weapon.getEnchant() < 3 || random.nextFloat() <= (1.01 - (weapon.getEnchant() / 12)) * 0.4) {
						weapon.setMinDmg((int) (Math.floor(weapon.getMinDmg() * 1.05f) + 1));
						weapon.setMaxDmg((int) (Math.floor(weapon.getMaxDmg() * 1.05f) + 1));
						weapon.addEnchant(1);
						e.setCurrentItem(weapon.build());
						enchantSuccess(player, weapon.getEnchant());
					} else {
						e.setCurrentItem(new ItemStack(Material.AIR));
						enchantFailed(player);
					}
				} else
					player.sendMessage(Util.colorCodes("&cYou must use this enchant on a weapon of the same tier."));
			} else if (new Armor(item).verify() && new ArmorEnchant(cursor).verify()) {
				Armor armor = new Armor(item);
				ArmorEnchant enchant = new ArmorEnchant(cursor);
				if (armor.getTier() == enchant.getTier()) {
					e.setCancelled(true);
					e.setCursor(new ItemStack(Material.AIR));
					if (armor.getEnchant() < 3 || random.nextFloat() <= (1 - (armor.getEnchant() / 12)) * 0.6) {
						armor.setHp((int) (Math.floor(armor.getHp() * 1.05f) + 1));
						armor.addEnchant(1);
						e.setCurrentItem(armor.build());
						enchantSuccess(player, armor.getEnchant());
					} else {
						e.setCurrentItem(new ItemStack(Material.AIR));
						enchantFailed(player);
					}
				} else
					player.sendMessage(Util.colorCodes("&cYou must use this enchant on an armor piece of the same tier."));
			}
		}
	}

	private void enchantSuccess(Player player, int enchant) {
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		if (enchant >= 4)
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.7f);
		if (enchant >= 12)
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0.5f);
		Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
		FireworkMeta fm = fw.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
		fw.setFireworkMeta(fm);
	}

	private void enchantFailed(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 0.6f);
	}
}
