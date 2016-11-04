package com.aegeus.game;

import com.aegeus.game.mobs.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;

public class Mobs implements Listener {

	// private final JavaPlugin PARENT;
	private final Random RANDOM = new Random();

	public Mobs(JavaPlugin parent) {
		// this.PARENT = parent;
		Bukkit.getScheduler().runTaskTimer(parent, () ->
			Arrays.stream(Spawner.getAll()).forEach((s) -> {
			if(s.canSpawn() && s.addUpdateTick() >= s.getUpdateDelay()) {
				s.setUpdateTick(0);
				if(RANDOM.nextFloat() <= s.getChanceToSpawn())
					s.getMob().create(s.getLocation());
			}
		}), 200, 200);
	}

}
