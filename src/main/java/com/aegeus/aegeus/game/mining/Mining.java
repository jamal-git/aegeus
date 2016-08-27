package com.aegeus.aegeus.game.mining;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagInt;

public class Mining implements Listener	{
	
	private JavaPlugin parent;
	Random rng = new Random();
	
	public Mining(JavaPlugin parent) {
		this.parent = parent;
	}
	
	public static ItemStack generatePickaxe(int level)	{
		if(level < 1 || level > 99)	{
			return null;
		}
		ItemStack pickaxe;
		if(level < 20)	pickaxe = new ItemStack(Material.WOOD_PICKAXE);
		else if(level < 40) pickaxe = new ItemStack(Material.STONE_PICKAXE);
		else if(level < 60) pickaxe = new ItemStack(Material.IRON_PICKAXE);
		else if(level < 80)	pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
		else pickaxe = new ItemStack(Material.GOLD_PICKAXE);
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(pickaxe);
		NBTTagCompound nbt = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagCompound pick = new NBTTagCompound();
		pick.set("level", new NBTTagInt(level));
		pick.set("xp", new NBTTagInt(0));
		pick.set("xpreq", new NBTTagInt(getXPNeeded(level + 1)));
		nbt.set("pickaxe", pick);
		nmsStack.setTag(nbt);
		pickaxe = CraftItemStack.asBukkitCopy(nmsStack);
		ItemMeta meta = pickaxe.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Level: " + ChatColor.AQUA + pick.getInt("level"));
		lore.add(ChatColor.GRAY + "EXP: 0 / " + getXPNeeded(pick.getInt("level") + 1));
		meta.setLore(lore);
		if(level < 20)	meta.setDisplayName(ChatColor.WHITE + "Basic Mining Drill");
		else if(level < 40) meta.setDisplayName(ChatColor.RED + "Aluminium Mining Drill");
		else if(level < 60) meta.setDisplayName(ChatColor.GREEN + "Reinforced Steel Mining Drill");
		else if(level < 80)	meta.setDisplayName(ChatColor.AQUA + "Crystal-Enriched Reinforced Mining Drill");
		else meta.setDisplayName(ChatColor.YELLOW + "Gold-Enriched Ultimate Mining Drill");
		pickaxe.setItemMeta(meta);
		return pickaxe;
	}
	
	public static int getXPNeeded(int target)	{
		if(target < 1 || target > 100)	return -1;
		int base = (int) (Math.pow(1.114, target) * 100);
		if(target >= 80)	{
			base += (target - 79) * 50000;
		}
		else if(target >= 60)	{
			base += (target - 59) * 15000;
		}
		else if(target >= 40)	{
			base += (target - 39) * 5000;
		}
		else if(target >= 20)	{
			base += (target - 19) * 1000;
		}
		else	{
			base += target * 50;
		}
		if(target == 9) base = 727; //727 pp Coookiezi
		return base;
	}
	
	private static void giveItem(Player p, Ore ore)	{
			ItemStack item = new ItemStack(ore.getOre());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ore.getName());
			item.setItemMeta(meta);
			if(p.getInventory().firstEmpty() != -1)	{
				p.getInventory().addItem(item);
			}
			else	{
				p.getWorld().dropItem(p.getLocation(), item);
				p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You do not have enough space in your inventory to hold more ore, so it was dropped on the ground.");
			}
	}
	
	@EventHandler
	public void onMine(BlockBreakEvent e)	{
		e.setCancelled(true);
		ItemStack pickaxe = e.getPlayer().getInventory().getItemInMainHand();
		Block mined = e.getBlock();
		Player p = e.getPlayer();
		if(pickaxe.getType() == Material.WOOD_PICKAXE || pickaxe.getType() == Material.STONE_PICKAXE || pickaxe.getType() == Material.IRON_PICKAXE || pickaxe.getType() == Material.DIAMOND_PICKAXE || pickaxe.getType() == Material.GOLD_PICKAXE)	{
			switch(mined.getType())	{
				case COAL_ORE:
				case REDSTONE_ORE:
				case IRON_ORE:
				case LAPIS_ORE:
				case DIAMOND_ORE:
				case EMERALD_ORE:
				case GOLD_ORE:
					calculateRewards(pickaxe, p, mined); 
					break;
					
					 
			}
		}
		if(p.getGameMode() == GameMode.CREATIVE) e.setCancelled(false);
	}
	
	private void calculateRewards(ItemStack pick, Player p, Block oreMined)	{
		//TODO add rewards for drill upgrades.
		switch(oreMined.getType())		{
			case COAL_ORE: //Rutile Ore
				if(getTier(pick.getType()) >= 3)	{
					giveItem(p, Ore.RUTILE);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case REDSTONE_ORE: //Bauxite Ore
				if(getTier(pick.getType()) >= 1)	{
					giveItem(p, Ore.BAUXITE);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case IRON_ORE:
				if(getTier(pick.getType()) >= 2)	{
					giveItem(p, Ore.IRON);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case LAPIS_ORE: //Lazurite Ore
				if(getTier(pick.getType()) >= 4)	{
					giveItem(p, Ore.LAZURITE);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case DIAMOND_ORE: //Crystal Ore
				if(getTier(pick.getType()) >= 3)	{
					giveItem(p, Ore.CRYSTAL);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case EMERALD_ORE: //Veridium Ore
				if(getTier(pick.getType()) == 5)	{
					giveItem(p, Ore.VERIDIUM);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case GOLD_ORE: //Gold Ore
				if(getTier(pick.getType()) >= 4)	{
					giveItem(p, Ore.GOLD);
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
					
		}
	}
	
	/**
	 * Returns the tier of a given item(should be pickaxes).  If the item is not a pickaxe, then it returns -1.
	 * @param item
	 * @return
	 */
	private int getTier(Material item)	{
		if(item == Material.WOOD_PICKAXE) return 1;
		else if(item == Material.STONE_PICKAXE) return 2;
		else if(item == Material.IRON_PICKAXE) return 3;
		else if(item == Material.DIAMOND_PICKAXE) return 4;
		else if(item == Material.GOLD_PICKAXE) return 5;
		else return -1;
	}
	
	private int getLevel(ItemStack pickaxe)	{
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(pickaxe);
		NBTTagCompound pickInfo = nmsStack.getTag().getCompound("pick");
		return pickInfo.getInt("level");
	}
}
