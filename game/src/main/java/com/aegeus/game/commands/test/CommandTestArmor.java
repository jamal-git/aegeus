package com.aegeus.game.commands.test;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.item.ItemArmor;
import com.aegeus.game.item.ItemRarity;
import com.aegeus.game.mobs.Mob;

public class CommandTestArmor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		ItemArmor armor = new ItemArmor(Material.CHAINMAIL_LEGGINGS);
		armor.setName("&dAgile Chain Leggings");
		armor.setHp(245);
		armor.setTier(2);
		armor.setRarity(ItemRarity.UNIQUE);
		player.getInventory().addItem(armor.build());
		
		Mob.Enum.XYLO.getMob().create(player.getWorld(), player.getLocation());
		
		return true;
	}

}
