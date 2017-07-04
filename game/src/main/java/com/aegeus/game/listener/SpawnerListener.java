package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class SpawnerListener implements Listener {
	private final Aegeus parent;

	public SpawnerListener(Aegeus parent) {
		this.parent = parent;
		Bukkit.getScheduler().runTaskTimer(parent, () -> parent.getSpawners().stream()
				.filter(Spawner::canSpawn).forEach(s -> s.get().spawn(s.getLocation())), 200, 200);
	}
}
