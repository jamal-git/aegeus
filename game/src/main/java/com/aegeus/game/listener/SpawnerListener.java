package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class SpawnerListener implements Listener {
	private final Aegeus parent;

	public SpawnerListener(Aegeus parent) {
		this.parent = parent;
		Bukkit.getScheduler().runTaskTimer(parent, () -> {
			for (Spawner s : parent.getSpawners())
				if (s.canSpawn()) {
					s.incrementCount().get().spawn(s.getLocation(), s);
				}
		}, 300, 300);
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		parent.getLogger().info(e.getMessage());
		if (e.getMessage().equals("/difficulty 0") && e.getPlayer().isOp()) {
			for (Spawner s : parent.getSpawners()) s.setCount(0);
		}
	}

	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e) {
		for (Spawner s : parent.getSpawners())
			if (s.getLocation().getChunk().equals(e.getChunk())) s.setCount(0);

	}
}
