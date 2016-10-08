package com.aegeus.game.commands.test;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aegeus.game.item.ItemArmor;
import com.aegeus.game.item.ItemRarity;

public class CommandTestArmor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		ItemArmor chelm = new ItemArmor(Material.DIAMOND_HELMET);
		chelm.setName("&dAnti-Cthulhu Helmet");
		chelm.setHp(325);
		chelm.setTier(5);
		chelm.setRarity(ItemRarity.UNIQUE);
		chelm.addLore("A Piece Of Holy Armour That Once Defeated Cthulhu");
		player.getInventory().addItem(chelm.build());
		
		ItemArmor cchest = new ItemArmor(Material.DIAMOND_CHESTPLATE);
		cchest.setName("&dAnti-Cthulhu Chestplate");
		cchest.setHp(375);
		cchest.setTier(5);
		cchest.setRarity(ItemRarity.UNIQUE);
		cchest.addLore("A Piece Of Holy Armour That Once Defeated Cthulhu");
		player.getInventory().addItem(cchest.build());
		
		ItemArmor cleggings = new ItemArmor(Material.DIAMOND_LEGGINGS);
		cleggings.setName("&dAnti-Cthulhu Leggings");
		cleggings.setHp(350);
		cleggings.setTier(5);
		cleggings.setRarity(ItemRarity.UNIQUE);
		cleggings.addLore("A Piece Of Holy Armour That Once Defeated Cthulhu");
		player.getInventory().addItem(cleggings.build());
		
		ItemArmor cboots = new ItemArmor(Material.DIAMOND_BOOTS);
		cboots.setName("&dAnti-Cthulhu Boots");
		cboots.setHp(325);
		cboots.setTier(5);
		cboots.setRarity(ItemRarity.UNIQUE);
		cboots.addLore("A Piece Of Holy Armour That Once Defeated Cthulhu");
		player.getInventory().addItem(cboots.build());
		
		return true;
	}

}
