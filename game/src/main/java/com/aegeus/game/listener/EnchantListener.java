package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.tool.*;
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
	private final Aegeus parent;

	public EnchantListener(Aegeus parent) {
		this.parent = parent;
	}

	@EventHandler
	private void onInteract(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().getType().equals(Material.EMPTY_MAP))
			e.setCancelled(true);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		ItemStack cursor = e.getCursor();

		// Weapon enchanting and runes
		if (item != null && !item.getType().equals(Material.AIR)) {
			if (new Weapon(item).verify()) {
				Weapon weapon = new Weapon(item);

				// Applying weapon enchantments
				if (e.isLeftClick() && cursor != null && !cursor.getType().equals(Material.AIR)
						&& new WeaponEnchant(cursor).verify()) {
					WeaponEnchant enchant = new WeaponEnchant(cursor);

					// Match weapon tier to enchantment
					if (weapon.getTier() != enchant.getTier())
						player.sendMessage(Util.colorCodes("&cYou must use this enchant on a weapon of the same tier."));
					else {
						e.setCancelled(true);
						e.setCursor(new ItemStack(Material.AIR));
						if (weapon.getEnchant() < 3 || random.nextFloat() <= (1.01 - (weapon.getEnchant() / 12)) * 0.4) {
							weapon.setMinDmg((int) (Math.floor(weapon.getMinDmg() * 1.05f) + 1));
							weapon.setMaxDmg((int) (Math.floor(weapon.getMaxDmg() * 1.05f) + 1));
							weapon.addEnchant(1);
							e.setCurrentItem(weapon.build());
							enchantSuccess(player, weapon.getEnchant());
						} else {
							if (weapon.getRune() != null)
								e.setCurrentItem(weapon.getRune().build());
							else
								e.setCurrentItem(new ItemStack(Material.AIR));
							enchantFailed(player);
						}
					}
				}

				// Equipping weapon runes
				if (e.isLeftClick() && weapon.getRune() == null && cursor != null && !cursor.getType().equals(Material.AIR)
						&& new Rune(cursor).verify()) {
					Rune rune = new Rune(cursor);

					if (!rune.getRuneType().getSlots().contains(Rune.RuneType.Slot.WEAPON))
						player.sendMessage(Util.colorCodes("&cThis rune cannot be equipped on weapons."));
					else {
						e.setCancelled(true);
						e.setCursor(new ItemStack(Material.AIR));
						weapon.setRune(rune);
						e.setCurrentItem(weapon.build());
						player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
					}
				}

				// Unequipping weapon runes
				if (e.isRightClick() && weapon.getRune() != null && (cursor == null || cursor.getType().equals(Material.AIR))) {
					e.setCancelled(true);
					e.setCursor(weapon.getRune().build());
					weapon.setRune(null);
					e.setCurrentItem(weapon.build());
					player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
				}
			} else if (new Armor(item).verify()) {
				Armor armor = new Armor(item);

				// Applying armor enchantments
				if (e.isLeftClick() && cursor != null && !cursor.getType().equals(Material.AIR)
						&& new ArmorEnchant(cursor).verify()) {
					ArmorEnchant enchant = new ArmorEnchant(cursor);

					// Match armor tier to enchantment
					if (armor.getTier() != enchant.getTier())
						player.sendMessage(Util.colorCodes("&cYou must use this enchant on an armor piece of the same tier."));
					else {
						e.setCancelled(true);
						e.setCursor(new ItemStack(Material.AIR));
						if (armor.getEnchant() < 3 || random.nextFloat() <= (1.01 - (armor.getEnchant() / 12)) * 0.4) {
							armor.setHp((int) (Math.floor(armor.getHp() * 1.05f) + 1));
							armor.setHpRegen((int) (Math.floor(armor.getHpRegen() * 1.05f) + 1));
							armor.addEnchant(1);
							e.setCurrentItem(armor.build());
							enchantSuccess(player, armor.getEnchant());
						} else {
							if (armor.getRune() != null)
								e.setCurrentItem(armor.getRune().build());
							else
								e.setCurrentItem(new ItemStack(Material.AIR));
							enchantFailed(player);
						}
					}
				}

				// Equipping armor runes
				if (e.isLeftClick() && armor.getRune() == null && cursor != null && !cursor.getType().equals(Material.AIR)
						&& new Rune(cursor).verify()) {
					Rune rune = new Rune(cursor);

					if (!rune.getRuneType().getSlots().contains(Rune.RuneType.Slot.ARMOR))
						player.sendMessage(Util.colorCodes("&cThis rune cannot be equipped on armor pieces."));
					else {
						e.setCancelled(true);
						e.setCursor(new ItemStack(Material.AIR));
						armor.setRune(rune);
						e.setCurrentItem(armor.build());
						player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
					}
				}

				// Unequipping armor runes
				if (e.isRightClick() && armor.getRune() != null && (cursor == null || cursor.getType().equals(Material.AIR))) {
					e.setCancelled(true);
					e.setCursor(armor.getRune().build());
					armor.setRune(null);
					e.setCurrentItem(armor.build());
					player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
				}
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
