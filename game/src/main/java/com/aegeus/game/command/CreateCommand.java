package com.aegeus.game.command;

import com.aegeus.game.command.util.Executor;
import com.aegeus.game.item.impl.ItemArmor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		ItemArmor armor = new ItemArmor(Material.DIAMOND_HELMET);
		ItemArmor armor2 = new ItemArmor(Material.DIAMOND_CHESTPLATE);
		armor.setHP(200);
		armor2.setHP(421);
		((Player) sender).getInventory().addItem(armor.build(), armor2.build());
		return true;
	}
}
