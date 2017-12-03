package com.aegeus.game.commands.item;

import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Weapon;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandRepair implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;

		Player player = (Player) sender;
		ItemStack tool = player.getInventory().getItemInMainHand();

		if (tool == null || tool.getType().equals(Material.AIR))
			player.sendMessage(Util.colorCodes("You're not holding anything."));
		else {
			if (Weapon.hasWeaponInfo(tool)) {
				Weapon weapon = new Weapon(tool);
				weapon.setDura(weapon.getMaxDura());
				weapon.setReserves(weapon.getMaxReserves());
				player.getInventory().setItemInMainHand(weapon.build());
			} else if (Armor.hasArmorInfo(tool)) {
				Armor armor = new Armor(tool);
				armor.setDura(armor.getMaxDura());
				armor.setReserves(armor.getMaxReserves());
				player.getInventory().setItemInMainHand(armor.build());
			}
		}

		return true;
	}
}
