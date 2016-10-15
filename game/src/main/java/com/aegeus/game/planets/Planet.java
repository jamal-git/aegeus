package com.aegeus.game.planets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.aegeus.game.data.Data;
import com.aegeus.game.data.PlayerData;
import com.aegeus.game.util.Helper;

public enum Planet {
	TERMINAL(Bukkit.getWorld("terminal"), "The Terminal", -1, "The hub of universal communication.", 0),
	XYLO(Bukkit.getWorld("planet_xylo"), "Xylo", 0, "A temporary planet filled with tons of gold resources.", 0);
	
	private World world;
	private String name;
	private int type;
	private String description;
	private int levelRequired;
	
	Planet(World world, String name, int type, String description, int levelRequired) {
		this.world = world;
		this.name = name;
		this.type = type;
		this.description = description;
		this.levelRequired = levelRequired;
	}
	
	public static Planet getByName(String name) {
		for(Planet p : values())
			if(p.getName().equalsIgnoreCase(name)) return p;
		return null;
	}
	
	public World getWorld() { return world; }
	public String getName() { return name; }
	public int getType() { return type; }
	public String getDescription() { return description; }
	public int getLevelRequired() { return levelRequired; }
	public Location getLocation() { return world.getSpawnLocation(); }
	
	public static void warpPlayer(Player player, Planet planet){
		PlayerData pd = Data.getPlayerData(player);
		if(planet.getLevelRequired() > 0
				&& Data.getPlayerData(player).getLevel() < planet.getLevelRequired()){
			player.sendMessage(Helper.colorCodes("&cYou are not a high enough level to warp to " + planet.getName() + "."));
		}
		else if(Data.getPlayerData(player).getCurrentPlanet().equals(planet)){
			player.sendMessage(Helper.colorCodes("&cYou are already on " + planet.getName() + "."));
		} else {
			player.sendMessage(Helper.colorCodes("&bYou are heading to " + planet.getName() + "..."));
			player.sendMessage(Helper.colorCodes("&7&o" + planet.getDescription()));
			player.teleport(planet.getWorld().getSpawnLocation());
			pd.setCurrentPlanet(planet);
		}
	}
}
