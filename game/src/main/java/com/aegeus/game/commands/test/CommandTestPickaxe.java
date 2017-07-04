package com.aegeus.game.commands.test;

import com.aegeus.game.item.ItemParser;
import com.aegeus.game.item.tool.Pickaxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Silvre on 6/30/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class CommandTestPickaxe implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player) || args.length == 0 || !sender.hasPermission("aegeus.test")) return false;
		Player p = (Player) sender;

		ItemStack pickaxe = ItemParser.parsePick(new Pickaxe(), args).build();

		if (!p.getInventory().addItem(pickaxe).isEmpty()) {
			p.getWorld().dropItem(p.getLocation(), pickaxe);
		}
		return true;
	}
}
