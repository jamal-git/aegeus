package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.ItemGold;
import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.profession.Ore;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

/**
 * TODO implement coordinate specific ores, so only specific ores will be minable
 */
public class MiningListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Aegeus parent;

	public MiningListener(Aegeus parent) {
		this.parent = parent;
	}

	@EventHandler
	public void onMine(BlockBreakEvent e) {
		e.setCancelled(true);
		Block b = e.getBlock();
		Player p = e.getPlayer();
		NBTTagCompound tag = CraftItemStack.asNMSCopy(p.getEquipment().getItemInMainHand()).getTag();
		if (tag.hasKey("AegeusInfo") && tag.getCompound("AegeusInfo").getString("type").equalsIgnoreCase("pickaxe")) {
			Pickaxe pick = new Pickaxe(p.getEquipment().getItemInMainHand());
			Location l = b.getLocation();
			pick.impo();
			Ore o = Ore.getOreByMaterial(b.getType());
			if (o != null) p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 0.7f, 1.0f);
			if (o != null && parent.getOres().containsKey(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ())) && o.isMinable(pick)) {
				b.setType(Material.STONE);
				if (!o.sameTier(pick) || random.nextInt(100) > 60 - pick.getLevel() % 20 * 2) {
					b.setType(Material.STONE);
					/*
					ENCHANT PROCS!
	                 */
					int amount = 1;
					boolean isDense = false;
					if (pick.getDoubleOre() > 0 && random.nextDouble() <= pick.getDoubleOre()) {
						amount++;
						p.sendMessage(Util.colorCodes("       &e&l*** DOUBLE ORE(x2) ***"));
					}
					if (pick.getTripleOre() > 0 && random.nextDouble() <= pick.getTripleOre()) {
						amount += 2;
						p.sendMessage(Util.colorCodes("       &e&l*** TRIPLE ORE(x3) ***"));
					}
					if (pick.getDenseFind() > 0 && random.nextDouble() <= pick.getDenseFind()) {
						isDense = true;
						p.sendMessage(Util.colorCodes("       &e&l*** DENSE FIND ***"));
					}
					if (pick.getGemFind() > 0 && random.nextDouble() <= pick.getGemFind()) {
						int goldDrop = (int) (o.getRandomGold() * (isDense ? Math.max(Math.log(pick.getDenseMultiplier() / Math.log(2)), 1.5) : 1));
						p.sendMessage(Util.colorCodes("       &a&l*** GOLD FIND +" + goldDrop + "G ***"));
						while (goldDrop > 64) {
							b.getWorld().dropItem(b.getLocation(), new ItemGold(64).build());
							goldDrop -= 64;
						}
						b.getWorld().dropItem(b.getLocation(), new ItemGold(goldDrop).build());
					}
					ItemStack stack = new ItemStack(o.getOre(), amount);
					ItemMeta meta = Bukkit.getItemFactory().getItemMeta(o.getOre());
					if (isDense) meta.setDisplayName(o.getName() + " x" + pick.getDenseMultiplier());
					else meta.setDisplayName(o.getName());
					meta.setLore(o.getLore());
					stack.setItemMeta(meta);
					if (!p.getInventory().addItem(stack).isEmpty()) {
						//Player doesnt have enough space in their inventory, dropping item on the floor.
						p.getWorld().dropItem(p.getLocation(), stack);
						p.sendMessage(Util.colorCodes("&7&oYour inventory is full, so your ore was dropped on the ground."));
					}
					int expGained = o.getRandom();
					if (pick.getLevel() != 100) {
						if (pick.addExp(expGained)) {
							p.sendMessage(Util.colorCodes("              &6&l*** LEVEL UP!&6 [&l" + pick.getTier().getColor() + pick.getLevel() + "&6] &l***"));
						}
						if (pick.checkForNextTier()) {
							p.sendMessage(Util.colorCodes("&7&oYour pick has ascended and is now a(n) " + pick.getTier().getColor() + "&n&o" + pick.getTier().getPickaxeName() + "&7&o!"));
						}
					}
					p.sendMessage(Util.colorCodes("              &e+" + expGained + " &lXP&7 [" + pick.getXp() + " / " + pick.getRequiredXp() + "]"));
				} else {
					p.sendMessage(Util.colorCodes("&7&oThe ore was destroyed while attempting the mine it."));
				}
				parent.getServer().getScheduler().scheduleSyncDelayedTask(parent, () -> b.setType(o.getOre()), o.getRespawnTime());
			} else if (o != null && !o.isMinable(pick) && parent.getOres().containsKey(b.getLocation())) {
				p.sendMessage(Util.colorCodes("&c&nYour pickaxe is not powerful enough to mine that yet!"));
			}
			p.getEquipment().setItemInMainHand(pick.build());
		} else if (p.getGameMode() == GameMode.CREATIVE) e.setCancelled(false);
	}
}