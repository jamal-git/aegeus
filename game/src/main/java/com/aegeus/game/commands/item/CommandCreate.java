package com.aegeus.game.commands.item;

import com.aegeus.game.item.ItemParser;
import com.aegeus.game.item.tool.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandCreate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.item")) return false;

		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("armor") || args[0].equalsIgnoreCase("weapon")) {
			Material material = Material.getMaterial(args[1]);
			if (material == null) return false;

			if (args[0].equalsIgnoreCase("armor"))
				player.getInventory().addItem(ItemParser.parseArmor(new Armor(material), args).build());
			if (args[0].equalsIgnoreCase("armor"))
				player.getInventory().addItem(ItemParser.parseWeapon(new Weapon(material), args).build());
		} else if (args[0].equalsIgnoreCase("pickaxe")) {
			player.getInventory().addItem(ItemParser.parsePick(new Pickaxe(), args).build());
			//	} else if (args[0].equalsIgnoreCase("rod")) {
			//		player.getInventory().addItem(ItemParser.parseRod(new FishRod(), args).build());
		} else if (args[0].equalsIgnoreCase("rune")) {
			try {
				Rune.RuneType type = Rune.RuneType.fromId(Integer.parseInt(args[1]));
				player.getInventory().addItem(new Rune(type).build());
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (args[0].equalsIgnoreCase("enchant")) {
			try {
				int amount = args.length < 4 ? 1 : Integer.parseInt(args[3]);
				ItemStack enchant;

				if (args[1].equalsIgnoreCase("armor")) {
					enchant = new ArmorEnchant(Integer.parseInt(args[2])).build();
				} else if (args[1].equalsIgnoreCase("weapon")) {
					enchant = new WeaponEnchant(Integer.parseInt(args[2])).build();
				} else return false;

				enchant.setAmount(amount);
				player.getInventory().addItem(enchant);
			} catch (NumberFormatException e) {
				return false;
			}
		} else return false;

		return true;
	}

}
