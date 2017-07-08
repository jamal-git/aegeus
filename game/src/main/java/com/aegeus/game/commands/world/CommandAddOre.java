package com.aegeus.game.commands.world;

import com.aegeus.game.Aegeus;
import com.aegeus.game.profession.Ore;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Silvre on 7/2/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class CommandAddOre implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (args.length != 0 || !sender.hasPermission("aegeus.world") || !(sender instanceof Player)) return false;
		Player p = (Player) sender;
		Block target = p.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 100);
		if (Ore.getOreByMaterial(target.getType()) != null) {
			Aegeus.getInstance().addOre(target);
			p.sendMessage(Util.colorCodes("&7Added ore at " + target.getX() + ", " + target.getY() + ", " + target.getZ()));
		}
		return true;
	}
}
