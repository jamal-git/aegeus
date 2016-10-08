package com.aegeus.game.commands.test;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.item.ItemRarity;
import com.aegeus.game.item.ItemWeapon;

public class CommandTestWeapon implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		ItemWeapon wep = new ItemWeapon(Material.DIAMOND_AXE);
		wep.setName("&dMythripper");
		wep.setDmg(152, 203);
		wep.setFireDmg(34);
		wep.setLifeSteal(0.34);
		wep.setTier(5);
		wep.setRarity(ItemRarity.RARE);
		
		player.getInventory().addItem(wep.build());
		return true;
	}

}
