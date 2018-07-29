package com.aegeus.game.command;

import com.aegeus.game.command.util.Executor;
import com.aegeus.game.item.Tiers;
import com.aegeus.game.item.impl.ItemWeapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		ItemWeapon weapon = Tiers.DefStats.get(0).weapon(0);
		ItemWeapon weapon2 = Tiers.DefStats.get(0).weapon(1);
		((Player) sender).getInventory().addItem(weapon.build(), weapon2.build());
		return true;
	}
}
