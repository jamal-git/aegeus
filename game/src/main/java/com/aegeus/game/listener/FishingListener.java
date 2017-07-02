package com.aegeus.game.listener;

import com.aegeus.game.item.tool.FishRod;
import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class FishingListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@EventHandler
	private void onFish(PlayerFishEvent e) {
		if (e.getState().equals(PlayerFishEvent.State.FISHING))
			Util.setBiteTime(e.getHook(), random.nextInt(50, 70));
		if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			e.setCancelled(true);
			e.getHook().remove();
			Player player = e.getPlayer();
			ItemStack tool = player.getEquipment().getItemInMainHand();
			if (tool != null && !tool.getType().equals(Material.AIR) && new FishRod(tool).verify()) {
				FishRod rod = new FishRod(tool);
				int xp = random.nextInt(240, 300);
				rod.addXp(xp);
				Util.notifyProfXp(player, xp, rod.getXp(), rod.getRequiredXp());
				if (rod.getXp() >= rod.getRequiredXp()) {
					rod.subtractXp(rod.getRequiredXp());
					rod.addLevel(1);
					Util.notifyProfLevel(player, rod.getLevel());
				}
				player.getInventory().setItemInMainHand(rod.build());
			}
		}
	}
}
