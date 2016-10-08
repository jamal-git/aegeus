package com.aegeus.game.mining;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.aegeus.common.Common;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

/**
 * TODO Comment everything
 *
 */
public class Mining implements Listener	{
	
	private JavaPlugin parent;
	Random rng = new Random();
	
	public Mining(JavaPlugin parent) {
		this.parent = parent;
	}
	
	public static int getXPNeeded(int target)	{
		if(target < 1 || target > 100)	return -1;
		int base = (int) (Math.pow(1.114, target) * 100);
		if(target > 80)	{
			base += (target - 79) * 50000;
			base += 20 * 15000;
			base += 20 * 5000;
			base += 20 * 1000;
			base += 20 * 50;
		}
		else if(target > 60)	{
			base += (target - 59) * 15000;
			base += 20 * 5000;
			base += 20 * 1000;
			base += 20 * 50;
		}
		else if(target > 40)	{
			base += (target - 39) * 5000;
			base += 20 * 1000;
			base += 20 * 50;
		}
		else if(target > 20)	{
			base += (target - 19) * 1000;
			base += 20 * 50;
		}
		else	{
			base += target * 50;
		}
		if(target == 9) base = 727; //727 pp Coookiezi
		return base;
	}
	
	private static void giveItem(Player p, Ore ore, int count)	{
			ItemStack item = new ItemStack(ore.getOre(), count);
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
					giveItem(p, Ore.RUTILE, 1);
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.RUTILE, p));
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case REDSTONE_ORE: //Bauxite Ore
				if(getTier(pick.getType()) >= 1)	{
					giveItem(p, Ore.BAUXITE, 1);
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.BAUXITE, p));
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case IRON_ORE:
				if(getTier(pick.getType()) >= 2)	{
					giveItem(p, Ore.IRON, 1);
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.IRON, p));
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case LAPIS_ORE: //Lazurite Ore
				if(getTier(pick.getType()) >= 4)	{
					giveItem(p, Ore.LAZURITE, 1);
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.LAZURITE, p));
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case DIAMOND_ORE: //Crystal Ore
				if(getTier(pick.getType()) >= 3)	{
					giveItem(p, Ore.CRYSTAL, 1);
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.CRYSTAL, p));
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case EMERALD_ORE: //Veridium Ore
				if(getTier(pick.getType()) == 5)	{
					giveItem(p, Ore.VERIDIUM, 1);
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.VERIDIUM, p));
				}
				else	{
					p.sendMessage("Your drill is not strong enough to mine this ore!");
				}
				break;
			case GOLD_ORE: //Gold Ore
				if(getTier(pick.getType()) >= 4)	{
					giveItem(p, Ore.GOLD, 1 );
					p.getInventory().setItemInMainHand(giveEXP(p.getInventory().getItemInMainHand(), Ore.GOLD, p));
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
		NBTTagCompound pickInfo = nmsStack.getTag().getCompound("pickaxe");
		return pickInfo.getInt("level");
	}
	
	private ItemStack giveEXP(ItemStack stack, Ore ore, Player p)	{
		net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		Material targetItem = stack.getType();
		String targetName = stack.getItemMeta().getDisplayName();
		NBTTagCompound nbt = nmsStack.getTag();
		NBTTagCompound pick = nbt.getCompound("pickaxe");
		int xp = pick.getInt("xp");
		int xpreq = pick.getInt("xpreq");
		int level = pick.getInt("level");
		int reward = 0;
		switch(ore)	{
			case RUTILE:
				reward = rng.nextInt(Common.RUTILE_RANGE * 2) + Common.RUTILE_EXP - Common.RUTILE_RANGE;
				xp += reward;
				break;
			case BAUXITE:
				reward = rng.nextInt(Common.BAUXITE_RANGE * 2) + Common.BAUXITE_EXP - Common.BAUXITE_RANGE;
				xp += reward;
				break;
			case IRON:
				reward = rng.nextInt(Common.IRON_RANGE * 2) + Common.IRON_EXP - Common.IRON_RANGE;
				xp += reward;
				break;
			case LAZURITE:
				reward = rng.nextInt(Common.LAZURITE_RANGE * 2) + Common.LAZURITE_EXP - Common.LAZURITE_RANGE;
				xp += reward;
				break;
			case CRYSTAL:
				reward = rng.nextInt(Common.CRYSTAL_RANGE * 2) + Common.CRYSTAL_EXP - Common.CRYSTAL_RANGE;
				xp += reward;
				break;
			case VERIDIUM:
				reward = rng.nextInt(Common.VERIDIUM_RANGE * 2) + Common.VERIDIUM_EXP - Common.VERIDIUM_RANGE;
				xp += reward;
				break;
			case GOLD:
				reward = rng.nextInt(Common.GOLD_RANGE * 2) + Common.GOLD_EXP - Common.GOLD_RANGE;
				xp += reward;
				break;
		}
		p.sendMessage(ChatColor.YELLOW + "		+" + reward + ChatColor.BOLD + " EXP " + ChatColor.GRAY + "[" + xp + " / " + xpreq + "]");
		if(xp >= xpreq)	{
			xp -= xpreq;
			xpreq = getXPNeeded(level + 1);
			level++;
			p.sendMessage(ChatColor.AQUA + "Your drill increased to level " + level);
			if(level == 20)	{
				targetItem = Material.STONE_PICKAXE;
				targetName = ChatColor.RED + "Aluminium Mining Drill";
			}
			else if(level == 40)	{
				targetItem = Material.IRON_PICKAXE;
				targetName = ChatColor.GREEN + "Reinforced Steel Mining Drill";
			}
			else if(level == 60)	{
				targetItem = Material.DIAMOND_PICKAXE;
				targetName = ChatColor.AQUA + "Crystal-Enriched Reinforced Mining Drill";
			}
			else if(level == 80)	{
				targetItem = Material.GOLD_PICKAXE;
				targetName = ChatColor.YELLOW + "Gold-Enriched Ultimate Mining Drill";
			}
		}
		pick.setInt("xp", xp);
		pick.setInt("xpreq", xpreq);
		pick.setInt("level", level);
		nbt.set("pickaxe", pick);
		nmsStack.setTag(nbt);
		ItemStack target = CraftItemStack.asBukkitCopy(nmsStack);
		if(!targetName.equals(target.getItemMeta().getDisplayName()))	{
			target.getItemMeta().setDisplayName(targetName);
		}
		if(target.getType() != targetItem)	{
			target.setType(targetItem);
		}
		ItemMeta meta = target.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Level: " + ChatColor.AQUA + level);
		lore.add(ChatColor.GRAY + "EXP: " + xp + " / " + xpreq);
		meta.setLore(lore);
		target.setItemMeta(meta);
		return target;
	}
}
