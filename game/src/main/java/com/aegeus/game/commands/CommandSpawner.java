package com.aegeus.game.commands;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import com.aegeus.game.stats.Stats;
import com.aegeus.game.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CommandSpawner implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.world")) return false;
		List<Spawner> list = Aegeus.getInstance().getSpawners();
		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("show") && args.length == 1) {
			for (Spawner s : list) {
				//noinspection deprecation
				player.sendBlockChange(s.getLocation(), Material.MOB_SPAWNER, (byte) 0);
			}
			return true;
		} else if (args[0].equalsIgnoreCase("hide") && args.length == 1) {
			for (Spawner s : list) {
				//noinspection deprecation
				player.sendBlockChange(s.getLocation(), Material.AIR, (byte) 0);
			}
			return true;
		} else if (args[0].equalsIgnoreCase("add") && args.length == 4) {
			Location l;
			if (args[1].equalsIgnoreCase("here"))
				l = player.getLocation().getBlock().getLocation();
			else if (args[1].equalsIgnoreCase("target"))
				l = player.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 100).getLocation();
			else return false;
			List<Stats> stats = new ArrayList<>();
			Arrays.stream(args[3].split(";")).map(x -> {
				try {
					String[] split = x.split(":");
					Class clazz = Class.forName("com.aegeus.game.stats." + split[0]);
					if (split.length >= 2) {
						Class inherit = Class.forName("com.aegeus.game.stats." + split[1]);
						return (Stats) clazz.getConstructor(Stats.class).newInstance((Stats) inherit.newInstance());
					} else
						return (Stats) clazz.newInstance();
				} catch (InstantiationException | ClassNotFoundException | IllegalAccessException
						| NoSuchMethodException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			}).forEach(stats::add);
			Spawner spawner = new Spawner(l);
			spawner.setMaxCount(Integer.valueOf(args[2]));
			spawner.setList(stats);
			Aegeus.getInstance().addSpawner(spawner);
			player.sendMessage(Util.colorCodes("&7Added a spawner at " + l.getX() + ", " + l.getY() + ", " + l.getZ()));
			//noinspection deprecation
			player.sendBlockChange(l, Material.MOB_SPAWNER, (byte) 0);
			return true;
		} else if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
			Location l;
			if (args[1].equalsIgnoreCase("here")) {
				l = player.getLocation().getBlock().getLocation();
			} else if (args[1].equalsIgnoreCase("target")) {
				l = player.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 100).getLocation();
			} else return false;
			if (Aegeus.getInstance().getSpawner(l) != null) {
				Aegeus.getInstance().removeSpawner(l);
				player.sendMessage(Util.colorCodes("&7Removed spawner at " + l.getX() + ", " + l.getY() + ", " + l.getZ()));
			} else player.sendMessage(Util.colorCodes("&7A spawner does not exist!"));
		}
		return false;
	}
}
