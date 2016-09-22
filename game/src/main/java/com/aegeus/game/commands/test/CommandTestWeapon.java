package com.aegeus.game.commands.test;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.common.Constants;
import com.aegeus.game.item.ItemRarity;
import com.aegeus.game.item.ItemWeapon;

public class CommandTestWeapon implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		ItemWeapon wep = new ItemWeapon(Material.DIAMOND_AXE);
		wep.setName("&dMordebit Axe of Ice Fire");
		wep.setDmg(165, 213);
		wep.setFireDmg(18);
		wep.setIceDmg(12);
		wep.setLifeSteal(0.09);
		wep.setTier(4);
		wep.setLevel(1);
		wep.setRarity(ItemRarity.RARE);
		
		player.getInventory().addItem(wep.build());
		return true;
	}

}
