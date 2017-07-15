package com.aegeus.game.commands.entity;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
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
		//Command executor must have aegeus.world permission and must be a player.
		if (!(sender instanceof Player)) return false;
		if (!sender.hasPermission("aegeus.world")) return false;
		List<Spawner> list = Aegeus.getInstance().getSpawners();
		Player player = (Player) sender;
        AgPlayer agPlayer = Aegeus.getInstance().getPlayer(player);
		if (args[0].equalsIgnoreCase("show") && args.length == 1) {
			//Show the player all the spawners using sendBlockChange
			for (Spawner s : list)
				//noinspection deprecation
				player.sendBlockChange(s.getLocation(), Material.MOB_SPAWNER, (byte) 0);
			player.sendMessage(Util.colorCodes("&7Showing all spawners."));
			return true;
		} else if (args[0].equalsIgnoreCase("hide") && args.length == 1) {
			//Hide all the spawners from the player using sendBlockChange
			for (Spawner s : list)
				//noinspection deprecation
				player.sendBlockChange(s.getLocation(), Material.AIR, (byte) 0);
			player.sendMessage(Util.colorCodes("&7Hiding all spawners."));
			return true;
		} else if (args[0].equalsIgnoreCase("add") && args.length == 4) {
			//Add a spawner to the list.
			Location l;
			if (args[1].equalsIgnoreCase("here"))
				//Player wants the spawner to be at the feet of him/herself
				l = player.getLocation().getBlock().getLocation();
			else if (args[1].equalsIgnoreCase("target"))
				//Player wants the spawner to be at where they are looking at.
				l = player.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 100).getLocation();
			else return false;
			List<Stats> stats = new ArrayList<>();
			//Parse spawner stats
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
			//Remove a spawner.
			Location l;
			if (args[1].equalsIgnoreCase("here")) {
				//Remove at the player's feet
				l = player.getLocation().getBlock().getLocation();
			} else if (args[1].equalsIgnoreCase("target")) {
				//Remove at the block the player is currently looking at
				l = player.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 100).getLocation();

			} else return false;
			if (Aegeus.getInstance().getSpawner(l) != null) {
				Aegeus.getInstance().removeSpawner(l);
				player.sendMessage(Util.colorCodes("&7Removed spawner at " + l.getX() + ", " + l.getY() + ", " + l.getZ()));
			} else player.sendMessage(Util.colorCodes("&7A spawner does not exist!"));
			return true;
		} else if (args[0].equalsIgnoreCase("remove") && args.length == 3) {
			//Remove a spawner in a radius around the player
			if (args[1].equalsIgnoreCase("radius")) {
				int radius = Integer.valueOf(args[2]);
				List<Spawner> spawners = Aegeus.getInstance().getSpawners();
				List<Spawner> removed = new ArrayList<>();
				for (int i = 0; i < spawners.size(); i++) {
					Location l = spawners.get(i).getLocation();
					if (l.getWorld().equals(player.getWorld()) && player.getLocation().distance(l) <= radius) {
						//noinspection deprecation
						player.sendBlockChange(spawners.get(i).getLocation(), Material.AIR, (byte) 0);
						removed.add(spawners.get(i));
						spawners.remove(spawners.get(i));
						i--;
					}
				}
				if (removed.size() < 20) {
					//Send all locations of spawners removed if the amount of spawners removed is less than 20.
					//Otherwise just tell the player how many spawners removed.
					for (Spawner s : removed) {
						Location l = s.getLocation();
						player.sendMessage(Util.colorCodes("&7Removed spawner at " + l.getX() + ", " + l.getY() + ", " + l.getZ()));
					}
				} else player.sendMessage(Util.colorCodes("&7Successfully removed " + removed.size() + " spawners."));
				Aegeus.getInstance().saveSpawners();
				return true;
			}
		}
		else if(args[0].equalsIgnoreCase("select") && args.length == 2) {
		    if(args[1].equalsIgnoreCase("here"))    {
		        for(Spawner s : Aegeus.getInstance().getSpawners()) {
		            if(s.getLocation().equals(player.getLocation().getBlock().getLocation()))   {
		                agPlayer.setEditSpawner(s);

                    }
                }
            }
        }
		return false;
	}
}