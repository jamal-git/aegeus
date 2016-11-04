package com.aegeus.game.commands.test;

import com.aegeus.game.item.ItemParser;
import com.aegeus.game.item.tool.Weapon;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTestWeapon implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(!sender.hasPermission("aegeus.test")) return false;
		if(args.length < 1) return false;
		
		Player player = (Player) sender;
		
		Material material = Material.getMaterial(args[0]);
		if(material == null) return false;
		
		Weapon weapon = ItemParser.parseWeapon(new Weapon(material), args);
	
		player.getInventory().addItem(weapon.build());
		
		return true;
	}

}
