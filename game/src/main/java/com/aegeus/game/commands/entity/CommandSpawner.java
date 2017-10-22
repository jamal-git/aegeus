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
import java.util.*;

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
		} else if (args[0].equalsIgnoreCase("add") && args.length == 3) {
			//Add a spawner to the list.
			Location l;
			if (args[1].equalsIgnoreCase("here"))
				//Player wants the spawner to be at the feet of him/herself
				l = player.getLocation().getBlock().getLocation();
			else if (args[1].equalsIgnoreCase("target"))
				//Player wants the spawner to be at where they are looking at.
				l = player.getTargetBlock(new HashSet<Material>(Collections.singletonList(Material.AIR)), 100).getLocation();
			else return false;
			List<Stats> stats = new ArrayList<>();
			//Parse spawner stats
			Arrays.stream(args[2].split(";")).map(x -> {
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
				l = player.getTargetBlock(new HashSet<Material>(Collections.singletonList(Material.AIR)), 100).getLocation();

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
		        Location target = player.getLocation().getBlock().getLocation();
		        for(Spawner s : Aegeus.getInstance().getSpawners()) {
		            if(s.getLocation().equals(target))   {
		                agPlayer.setEditSpawner(s);
                        agPlayer.sendMessage(Util.colorCodes("&7Selected spawner at " + target.getX() + ", " + target.getY() + ", " + target.getZ()));
                        return true;
                    }
                }
                agPlayer.sendMessage(Util.colorCodes("&cThere is no spawner here!"));
		        return true;
            }
            else if(args[1].equalsIgnoreCase("target")) {
		        Location target = player.getTargetBlock(new HashSet<>(Collections.singletonList(Material.AIR)), 100).getLocation();
		        for(Spawner s : Aegeus.getInstance().getSpawners()) {
		            if(s.getLocation().equals(target))  {
		                agPlayer.setEditSpawner(s);
		                agPlayer.sendMessage(Util.colorCodes("&7Selected spawner at " + target.getX() + ", " + target.getY() + ", " + target.getZ()));
		                return true;
                    }
                }
                agPlayer.sendMessage(Util.colorCodes("&cThere is no spawner there!"));
		        return true;
            }
        }
        else if(args[0].equalsIgnoreCase("deselect") && args.length == 1)   {
		    agPlayer.setEditSpawner(null);
		    agPlayer.sendMessage(Util.colorCodes("&7Deselected current spawner."));
		    return true;
        }
        else if(args[0].equalsIgnoreCase("edit") && args.length == 3)   {
		    if(agPlayer.getEditSpawner() == null)   {
		        agPlayer.sendMessage(Util.colorCodes("&cYou dont have a spawner selected!"));
            }
            else    {
		        Spawner spawner = agPlayer.getEditSpawner();
		        switch(args[1]) {
                    case "delay":
                        spawner.setDelayCount(Integer.valueOf(args[2]));
                        agPlayer.sendMessage(Util.colorCodes("&aSuccessfully set spawn delay to &e" + args[2] + "&a."));
                        break;
                    case "count":
                        spawner.setMaxCount(Integer.valueOf(args[2]));
                        agPlayer.sendMessage(Util.colorCodes("&aSuccessfully set max spawn count to &e" + args[2] + "&a."));
                        break;
                    case "setStats":
                        List<Stats> stats = new ArrayList<>();
                        Arrays.stream(args[2].split(";")).map(x -> {
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
                        spawner.setList(stats);
                        agPlayer.sendMessage(Util.colorCodes("&aSuccessfully set stats list."));
                        break;
                    case "addStat":
                        try {
                            String[] split = args[2].split(":");
                            Class clazz = Class.forName("com.aegeus.game.stats." + split[0]);
                            if(split.length >= 2)   {
                                Class inherit = Class.forName("com.aegeus.game.stats." + split[1]);
                                spawner.add((Stats) clazz.getConstructor(Stats.class).newInstance((Stats) inherit.newInstance()));
                            }
                            else spawner.add((Stats) clazz.newInstance());
                            agPlayer.sendMessage(Util.colorCodes("&aSuccessfully added stat to spawner."));
                        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException
                                    | NoSuchMethodException | InstantiationException e) {
                            e.printStackTrace();
                        }
                }
            }
            Aegeus.getInstance().saveSpawners();
            return true;
        }
        else if(args[0].equalsIgnoreCase("info") && args.length == 1)   {
		    if(agPlayer.getEditSpawner() == null)   {
		        agPlayer.sendMessage(Util.colorCodes("&cYou don't have a spawner selected!"));
            }
            else    {
		        Spawner spawner = agPlayer.getEditSpawner();
		        Location l = spawner.getLocation();
		        agPlayer.sendMessage("");
                agPlayer.sendMessage("");
		        agPlayer.sendMessage(Util.colorCodes("&3          -Spawner Info-          "));
		        agPlayer.sendMessage(Util.colorCodes("&3----------------------------------"));
		        agPlayer.sendMessage(Util.colorCodes("&3Memory Address: &a" + spawner.toString()));
		        agPlayer.sendMessage(Util.colorCodes("&3Coordinates: &a" + l.getX() + "&3, &a" + l.getY() + "&3, &a" + l.getZ()));
		        agPlayer.sendMessage(Util.colorCodes("&3Current Mob Count: &a" + spawner.getCount()));
		        agPlayer.sendMessage(Util.colorCodes("&3Max Mob Count: &a" + spawner.getMaxCount()));
		        agPlayer.sendMessage(Util.colorCodes("&3Respawn Delay: &a" + spawner.getDelayCount()));
		        agPlayer.sendMessage(Util.colorCodes("&3Current Delay Count: &a" + spawner.getCurrentDelay()));
		        agPlayer.sendMessage(Util.colorCodes("&3Spawnable Entities:"));
		        for(Stats s : spawner.getList())    {
		            if(s.getParent() != null)
		                agPlayer.sendMessage(Util.colorCodes("      &e" + s.getClass().getSimpleName() + ":" + s.getParent().getClass().getSimpleName()));
		            else agPlayer.sendMessage(Util.colorCodes("      &e" + s.getClass().getSimpleName()));
                }
                agPlayer.sendMessage(Util.colorCodes("&3----------------------------------"));
                agPlayer.sendMessage("");
                agPlayer.sendMessage("");
            }
            return true;
        }
        agPlayer.sendMessage(Util.colorCodes("&cInvalid arguments!"));
		return false;
	}
}