package com.aegeus.game.listener;

import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.profession.Ore;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * TODO implement coordinate specific ores, so only specific ores will be minable
 *
 */
public class MiningListener implements Listener {

	Random rng = new Random();
	private JavaPlugin parent;

	public MiningListener(JavaPlugin parent) {
		this.parent = parent;
	}

    @EventHandler
    public void onMine(BlockBreakEvent e)   {
	    e.setCancelled(true);
	    Block b = e.getBlock();
	    Player p = e.getPlayer();
	    NBTTagCompound tag = CraftItemStack.asNMSCopy(p.getEquipment().getItemInMainHand()).getTag();
	    if(tag.hasKey("AegeusInfo") && tag.getCompound("AegeusInfo").getString("type").equalsIgnoreCase("pickaxe")) {
	        Pickaxe pick = new Pickaxe(p.getEquipment().getItemInMainHand());
	        pick.impo();
	        Ore o = Ore.getOreByMaterial(b.getType());
	        if(o != null) p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 0.7f, 1.0f);
	        if(o != null && o.isMinable(pick))  {
	            b.setType(Material.STONE);
	            if(!o.sameTier(pick) || rng.nextInt(100) > 60 - pick.getLevel() % 20 * 2) {
	                b.setType(Material.STONE);
                    ItemStack stack = new ItemStack(o.getOre(), 1);
                    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(o.getOre());
                    meta.setDisplayName(o.getName());
                    meta.setLore(o.getLore());
                    stack.setItemMeta(meta);
                    if (!p.getInventory().addItem(stack).isEmpty()) {
                        //Player doesnt have enough space in their inventory, dropping item on the floor.
                        p.getWorld().dropItem(p.getLocation(), stack);
                        p.sendMessage(Util.colorCodes("&7&oYour inventory is full, so your ore was dropped on the ground."));
                    }
                    int expGained = o.getRandom();
                    if(pick.getLevel() != 100) {
                        if (pick.addExp(expGained)) {
                            p.sendMessage(Util.colorCodes("              &6&l*** LEVEL UP!&6 [&l" + pick.getTier().getColor() + pick.getLevel() + "&6] &l***"));
                        }
                        if (pick.checkForNextTier()) {
                            p.sendMessage(Util.colorCodes("&7&oYour pick has ascended and is now a(n) " + pick.getTier().getColor() + "&n&o" + pick.getTier().getPickaxeName() + "&7&o!"));
                        }
                    }
                        p.sendMessage(Util.colorCodes("              &e+" + expGained + " &lXP&7 [" + pick.getXp() + " / " + pick.getRequiredXp() + "]"));
                }
                else    {
	                p.sendMessage(Util.colorCodes("&7&oThe ore was destroyed while attempting the mine it."));
                }
                parent.getServer().getScheduler().scheduleSyncDelayedTask(parent, () -> b.setType(o.getOre()), o.getRespawnTime());
            }
            else if(o != null && !o.isMinable(pick))    {
	            p.sendMessage(Util.colorCodes("&c&nYour pickaxe is not powerful enough to mine that yet!"));
            }
            p.getEquipment().setItemInMainHand(pick.build());
        }
    }
}
