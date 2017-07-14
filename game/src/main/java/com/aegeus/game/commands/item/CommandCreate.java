package com.aegeus.game.commands.item;

import com.aegeus.game.item.ItemParser;
import com.aegeus.game.item.Tier;
import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.tool.Enchant;
import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.item.tool.Weapon;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			if (args[0].equalsIgnoreCase("weapon"))
				player.getInventory().addItem(ItemParser.parseWeapon(new Weapon(material), args).build());
		} else if (args[0].equalsIgnoreCase("pickaxe")) {
			player.getInventory().addItem(ItemParser.parsePick(new Pickaxe(), args).build());
		} else if (args[0].equalsIgnoreCase("enchant")) {
			Tier tier = Tier.fromTier(Integer.parseInt(args[1]));
			int type = Integer.parseInt(args[2]);
			int amount = args.length < 4 ? 1 : Integer.parseInt(args[3]);

			player.getInventory().addItem(new Enchant(tier, type, amount).build());
		}

		return true;
	}

}
