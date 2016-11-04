package com.aegeus.game.commands.test;

import com.aegeus.game.item.tool.Armor;
import com.aegeus.game.item.ItemParser;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTestArmor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(!sender.hasPermission("aegeus.test")) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		
		Material material = Material.getMaterial(args[0]);
		if(material == null) return false;

		Armor armor = ItemParser.parseArmor(new Armor(material), args);
	
		player.getInventory().addItem(armor.build());
		
		return true;
	}

}
