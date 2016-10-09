package com.aegeus.game.planets;

import org.bukkit.Location;
import org.bukkit.World;

public interface Planet {
	
	public enum Enum {
		TERMINAL(new Terminal()),
		XYLO(new PlanetXylo());
	
		private final Planet planet;
		
		Enum(Planet planet) { this.planet = planet; }
		
		public static Enum byName(String name) {
			for(Enum planet : values())
				if(planet.getPlanet().getName().equalsIgnoreCase(name))
					return planet;
			return null;
		}
		
		public Planet getPlanet() { return planet; }
	}
	
	public World getWorld();
	public String getName();
	public int getType();
	public String getDescription();
	public int getLevelRequired();
	public Location getLocation();
}
