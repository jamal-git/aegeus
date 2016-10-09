package com.aegeus.game.planets;

import org.bukkit.entity.Player;

import com.aegeus.game.player.PlayerData;
import com.aegeus.game.util.Helper;

public class PlanetManager {
	
	public static void warpPlayer(Player player, Planet.Enum p){
		Planet planet = p.getPlanet();
		if(planet.getLevelRequired() > 0
				&& PlayerData.get(player).getLevel() < planet.getLevelRequired()){
			player.sendMessage(Helper.colorCodes("&cYou are not a high enough level to warp to " + planet.getName() + "."));
		}
		else if(PlayerData.get(player).getCurrentPlanet().equals(planet)){
			player.sendMessage(Helper.colorCodes("&cYou are already on " + planet.getName() + "."));
		} else {
			player.sendMessage(Helper.colorCodes("&bYou are heading to " + planet.getName() + "..."));
			player.sendMessage(Helper.colorCodes("&7&o" + planet.getDescription()));
			player.teleport(planet.getLocation());
			PlayerData.get(player).setCurrentPlanet(planet);
		}
	}
	
}
