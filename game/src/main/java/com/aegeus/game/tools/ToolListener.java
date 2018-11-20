package com.aegeus.game.tools;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToolListener implements Listener {
	private List<Trap> traps = new ArrayList<>();

	public ToolListener() {
		Bukkit.getScheduler().runTaskTimer(Aegeus.getInstance(), () -> {
			for (Trap trap : traps.stream().filter(t -> !t.hasEntity()).collect(Collectors.toList()))
				for (Entity e : trap.getNearby()) {
					if (e instanceof LivingEntity) {
						AgLiving info = Aegeus.getInstance().getEntities().getLiving((LivingEntity) e);
						if (!info.isTrapped()) {
							info.trap(trap);
							trap.getWorld().playSound(trap.getLoc(), Sound.BLOCK_PISTON_EXTEND, 1, -1);
							trap.getBlock().setType(Material.NETHER_FENCE);
							trap.getBlock().getRelative(BlockFace.UP, 1).setType(Material.NETHER_FENCE);
							trap.getBlock().getRelative(BlockFace.DOWN, 1).setType(Material.NETHER_BRICK);
						}
					}
				}
		}, 10, 10);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent evt) {
		Block block = evt.getBlock();

		if (block.getType() == Material.TRAP_DOOR) {
			Block above = block.getRelative(BlockFace.UP, 1);
			Block below = block.getRelative(BlockFace.DOWN, 1);

			if (above.getType() != Material.AIR || below.getType() == Material.AIR) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(Util.colorCodes("&cThere's no room for a trap to be placed here."));
			} else
				evt.getPlayer().sendMessage(Util.colorCodes("&f&lTrap placed."
						+ "\n&7Left-click the trap to remove it."
						+ "\n&7Right-click the trap to enable it."));
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		Block block = evt.getClickedBlock();

		if (block != null && block.getType() == Material.TRAP_DOOR) {
			evt.setCancelled(true);

			Block above = block.getRelative(BlockFace.UP, 1);
			Block below = block.getRelative(BlockFace.DOWN, 1);

			if (evt.getAction() == Action.LEFT_CLICK_BLOCK)
				block.breakNaturally();
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK
					&& above.getType() == Material.AIR && below.getType() != Material.AIR) {
				evt.getPlayer().sendMessage(Util.colorCodes("&7Trap enabled."));
				above.setType(Material.getMaterial(101));
				block.setType(Material.getMaterial(101));
				below.setType(Material.WOOD);
				traps.add(new Trap(block.getLocation(), 12));
			}
		}
	}
}
