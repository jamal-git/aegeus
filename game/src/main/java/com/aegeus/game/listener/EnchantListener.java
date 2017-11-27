package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Enchant;
import com.aegeus.game.item.tool.Weapon;
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

public class EnchantListener implements Listener {
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

			// Applying enchantments
			if (e.isLeftClick() && cursor != null && !cursor.getType().equals(Material.AIR)
					&& Enchant.hasEnchantInfo(cursor)) {
				Enchant enchant = new Enchant(cursor);

				// Weapon enchanting
				if (enchant.getType() == Enchant.WEAPON && Weapon.hasWeaponInfo(item)) {
					Weapon weapon = new Weapon(item);
					if (weapon.getTier() != enchant.getTier())
						player.sendMessage(Util.colorCodes("&cYou must use this enchant on a weapon of the same tier."));
					else {
						e.setCancelled(true);
						e.setCursor(new ItemStack(Material.AIR));
						if (weapon.getEnchant() < 3 || Util.rFloat() <= (1.01 - (weapon.getEnchant() / 12)) * 0.4) {
							weapon.setMinDmg((int) (Math.floor(weapon.getMinDmg() * 1.05f) + 1));
							weapon.setMaxDmg((int) (Math.floor(weapon.getMaxDmg() * 1.05f) + 1));
							weapon.addEnchant(1);
							e.setCurrentItem(weapon.build());
							enchantSuccess(player, weapon.getEnchant());
						} else {
							enchantFailed(player);
						}
					}
				}

				// Armor enchanting
				if (enchant.getType() == Enchant.ARMOR && Armor.hasArmorInfo(item)) {
					Armor armor = new Armor(item);
					if (armor.getTier() != enchant.getTier())
						player.sendMessage(Util.colorCodes("&cYou must use this enchant on an armor piece of the same tier."));
					else {
						e.setCancelled(true);
						e.setCursor(new ItemStack(Material.AIR));
						if (armor.getEnchant() < 3 || Util.rFloat() <= (1.01 - (armor.getEnchant() / 12)) * 0.4) {
							armor.setHp((int) (Math.floor(armor.getHp() * 1.05f) + 1));
							armor.setHpRegen((int) (Math.floor(armor.getHpRegen() * 1.05f) + 1));
							armor.addEnchant(1);
							e.setCurrentItem(armor.build());
							enchantSuccess(player, armor.getEnchant());
						} else {
							enchantFailed(player);
						}
					}
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
