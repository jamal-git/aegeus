package com.aegeus.game.listener;

import com.aegeus.game.item.tool.Pickaxe;
import com.aegeus.game.mining.Ore;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	        p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 0.7f, 1.0f);
	        Pickaxe pick = new Pickaxe();
	        pick.impo();
	        Ore o = Ore.getOreByMaterial(b.getType());
	        if(o != null && o.isMinable(pick))  {
	            b.setType(Material.STONE);
	            if(!o.sameTier(pick) || rng.nextInt(100) < 80 - pick.getLevel() % 20 * 2) {
	                b.setType(Material.STONE);
                    ItemStack stack = new ItemStack(o.getOre(), 1);
                    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(o.getOre());
                    meta.setDisplayName(o.getName());
                    meta.setLore(o.getLore());
                    stack.setItemMeta(meta);
                    if (!p.getInventory().addItem(stack).isEmpty()) {
                        //Player doesnt have enough space in their inventory, dropping item on the floor.
                        p.getWorld().dropItem(p.getLocation(), stack);
                        p.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "Your inventory is full, so your ore was dropped on the ground.");
                    }
                    int expGained = o.getRandom();
                    if(pick.addExp(expGained))  {
                        p.sendMessage(ChatColor.GREEN + "Your pick has levelled up to level " + ChatColor.BLUE + pick.getLevel() + ChatColor.GREEN + "!");
                    }
                    if(pick.checkForNextTier()) {
                        p.sendMessage(ChatColor.BOLD + "" + ChatColor.ITALIC + ChatColor.GREEN + "You pick has ascended and is now a(n) "
                                + ChatColor.UNDERLINE + pick.getTier().getColor() + pick.getTier().name() + " Pickaxe" + ChatColor.RESET + ChatColor.BOLD + "" + ChatColor.ITALIC + ChatColor.GREEN + "!");
                    }
                    p.sendMessage(Util.colorCodes("    &e&l+&r&e" + expGained + " EXP"));
                    p.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "  " + ChatColor.RESET + ChatColor.YELLOW + "[" + pick.getXp() + " / " + pick.getXPRequired() + "]");
                }
                else    {
	                p.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "The ore was destroyed while attempting the mine it.");
                }
                parent.getServer().getScheduler().scheduleSyncDelayedTask(parent, () -> b.setType(o.getOre()), o.getRespawnTime());
            }
            else if(o != null && !o.isMinable(pick))    {
	            p.sendMessage(ChatColor.RED + "Your pickaxe is not powerful enough to mine that yet!");
            }
            else    {
            }
            p.getEquipment().setItemInMainHand(pick.build());
        }
    }
}
