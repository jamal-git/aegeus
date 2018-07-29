package com.aegeus.game.command;

import com.aegeus.game.command.util.Executor;
import com.aegeus.game.item.impl.ItemWeapon;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		ItemWeapon weapon = new ItemWeapon(Material.DIAMOND_AXE);
		weapon.setDmg(100, 200);
		((Player) sender).getInventory().addItem(weapon.build());
		return true;
	}
}
