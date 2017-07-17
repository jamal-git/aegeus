package com.aegeus.game.listener;

import com.aegeus.game.Aegeus;
import com.aegeus.game.item.Items;
import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.profession.Ore;
import com.aegeus.game.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

public class MiningListener implements Listener {
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();
	private final Aegeus parent;

	public MiningListener(Aegeus parent) {
		this.parent = parent;
	}

	@EventHandler
	public void onMine(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		e.setCancelled(true);
		Block b = e.getBlock();
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		try {
			if (Pickaxe.hasPickaxeInfo(item)) {
				//Verify held item is a pickaxe
				Pickaxe pick = new Pickaxe(item);
				pick.impo();
				Ore o = Ore.getOreByMaterial(b.getType());
				if (o != null && o.isMinable(pick)) {
					//Mined block is a registered ore and was mined with a pick, give do stuff.
					b.setType(Material.STONE);
					if (!o.sameTier(pick) || random.nextInt(100) > 60 - pick.getLevel() % 20 * 2) {
						b.setType(Material.STONE);
						/*
						ENCHANT PROCS!
                        */
						int amount = 1;
						boolean isDense = false;
						if (pick.getDoubleOre() > 0 && random.nextDouble() <= pick.getDoubleOre()) {
							//Triple ore proc
							amount++;
							p.sendMessage(Util.colorCodes("       &e&l*** DOUBLE ORE(x2) ***"));
						}
						if (pick.getTripleOre() > 0 && random.nextDouble() <= pick.getTripleOre()) {
							//Triple ore proc
							amount += 2;
							p.sendMessage(Util.colorCodes("       &e&l*** TRIPLE ORE(x3) ***"));
						}
						if (pick.getDenseFind() > 0 && random.nextDouble() <= pick.getDenseFind()) {
							//Dense find proc
							isDense = true;
							p.sendMessage(Util.colorCodes("       &e&l*** DENSE FIND ***"));
						}
						if (pick.getGemFind() > 0 && random.nextDouble() <= pick.getGemFind()) {
							//Gem find proc
							//Dense find and multiplier affects the amount of gold drop, logarithmically base 10.
							int goldDrop = (int) (o.getRandomGold() * (isDense ? Math.max(Math.log(pick.getDenseMultiplier()), 1.5) : 1));
							p.sendMessage(Util.colorCodes("       &a&l*** GOLD FIND +" + goldDrop + "G ***"));
							while (goldDrop > 64) {
								//Drop multiple stacks if the amount of gold to drop is greater than one stack
								b.getWorld().dropItem(b.getLocation(), Items.getGold(64));
								goldDrop -= 64;
							}
							b.getWorld().dropItem(b.getLocation(), Items.getGold(goldDrop));
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
							//Add exp if the level is less than 100.
							if (pick.addExp(expGained)) {
								//Pickaxe has levelled up, send a message
								p.sendMessage(Util.colorCodes("              &6&l*** LEVEL UP!&6 [&l" + pick.getTier().getColor() + pick.getLevel() + "&6] &l***"));
							}
							if (pick.checkForNextTier()) {
								//The pickaxe has ascended to a new tier, shoot a firework and send a message.
								p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.7f);
								Firework f = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
								FireworkMeta fm = f.getFireworkMeta();
								fm.setPower(1);
								fm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).trail(true).withColor(Color.RED, Color.YELLOW).flicker(true).build());
								f.setFireworkMeta(fm);
								p.sendMessage(Util.colorCodes("&7&oYour pick has ascended and is now a(n) " + pick.getTier().getColor() + "&n&o" + pick.getTier().getPickaxeName() + "&7&o!"));
							}
						}
						//Message exp gained, plus progress
						p.sendMessage(Util.colorCodes("              &e+" + expGained + " &lXP&7 [" + pick.getXp() + " / " + pick.getRequiredXp() + "]"));
					} else {
						//Ore failed to be mined, better luck next time!
						p.sendMessage(Util.colorCodes("&7&oThe ore was destroyed while attempting the mine it."));
					}
					//Set respawn timer to respective time.
					parent.getServer().getScheduler().scheduleSyncDelayedTask(parent, () -> b.setType(o.getOre()), o.getRespawnTime());
				} else if (o != null && !o.isMinable(pick)) {
					//Player is trying to mine ore with a pickaxe that is not strong enough.
					p.sendMessage(Util.colorCodes("&c&nYour pickaxe is not powerful enough to mine that yet!"));
				}
				p.getEquipment().setItemInMainHand(pick.build());
			} else if (p.getGameMode() == GameMode.CREATIVE) e.setCancelled(false);
		} catch (Exception fuckyou) {
			//fuck exceptions i dont really givea fuck
		}
	}
}