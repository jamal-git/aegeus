package com.aegeus.game.commands;

import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandOpenEntity implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.entity")) return false;

		Player player = (Player) sender;
		Entity entity = Util.getTargetEntity(player);
		if (!(entity instanceof LivingEntity))
			return false;
		else {
			LivingEntity lEntity = (LivingEntity) entity;
			Inventory inv = Bukkit.createInventory(player, 9);
			inv.addItem(
					lEntity.getEquipment().getHelmet(),
					lEntity.getEquipment().getChestplate(),
					lEntity.getEquipment().getLeggings(),
					lEntity.getEquipment().getBoots(),
					lEntity.getEquipment().getItemInMainHand(),
					lEntity.getEquipment().getItemInOffHand());
			player.openInventory(inv);
		}

		return true;
	}
}
