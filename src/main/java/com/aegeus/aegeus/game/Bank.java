package com.aegeus.aegeus.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.aegeus.aegeus.player.PlayerData;
import com.aegeus.aegeus.util.InventorytoBase64;

public class Bank implements Listener{
	private JavaPlugin parent;
	
	/**
	 * Main constructor
	 * @param parent
	 */
	public Bank(JavaPlugin parent) {
		this.parent = parent;
	}
	
	@EventHandler
	public void onOpenChest(InventoryOpenEvent e)	{
		if(e.getInventory().getType() == InventoryType.ENDER_CHEST)	{
			//The player has opened a vanilla ender chest.  Let's cancel it and give them our custom ender chest with more customizability.
			e.setCancelled(true);
			Player p = (Player) e.getPlayer();
			Inventory inv = Statistics.playerData.get(p).getBank() != null ? Statistics.playerData.get(p).getBank() : generateEmptyBank(p);
			p.openInventory(inv);
		}
	}
	
	@EventHandler
	public void onCloseChest(InventoryCloseEvent e)	{
		if(e.getInventory().getName().equalsIgnoreCase("Bank"))	{
			//The player has closed our custom bank inventory.  Spit out the contents of the array as a string to the console.
			PlayerData playerinfo = Statistics.playerData.get(e.getPlayer());
			playerinfo.setBank(e.getInventory());
		}
	}
	
	@EventHandler
	public void onClickEvent(InventoryClickEvent e)	{
		if(e.getCurrentItem().getType() == Material.GOLD_BLOCK)	{
			e.setCancelled(true);
			Bukkit.getScheduler().runTask(parent, new Runnable()	{
				public void run()	{
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage("" + ChatColor.GRAY + ChatColor.ITALIC + "Enter the amount of gold that you would like to " + ChatColor.BOLD + "withdraw" + ChatColor.GRAY + ChatColor.ITALIC + ".");
					Statistics.playerData.get(e.getWhoClicked()).isBankWithdraw = true;
				}
			}
					);
		}
	}
	
	@EventHandler
	public void onBlockInteractEvent(PlayerInteractEvent e)	{
		ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && itemInHand.getType() == Material.NETHER_STAR && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bank Upgrade Module") && e.getClickedBlock().getType() == Material.ENDER_CHEST)	{
			e.setCancelled(true);
			if(Statistics.playerData.get(e.getPlayer()).getBank().getSize() != 54)	{
				ItemStack module = new ItemStack(Material.NETHER_STAR);
				ItemMeta meta = module.getItemMeta();
				meta.setDisplayName(ChatColor.GOLD + "Bank Upgrade Module");
				module.setItemMeta(meta);
				e.getPlayer().getInventory().remove(module);
				Statistics.playerData.get(e.getPlayer()).setBank(upgradeBank(Statistics.playerData.get(e.getPlayer()).getBank(), e.getPlayer()));
				e.getPlayer().sendMessage("" + ChatColor.AQUA + "Your bank has been upgraded to " + ChatColor.GREEN + ChatColor.BOLD + Statistics.playerData.get(e.getPlayer()).getBank().getSize() + ChatColor.AQUA + " slots!");
			}
		}
	}
	
	/**
	 * Method used for upgrade your bank 9 slots bigger.  Only call this method if you know they have not reached the max size of bank.
	 * @param source
	 * @param owner
	 * @return Upgraded inventory with original contents.
	 */
	private Inventory upgradeBank(Inventory source, InventoryHolder owner)	{
		Inventory target = null;
		switch(source.getSize())	{
			case 9:
				target = Bukkit.createInventory(owner, 18, "Bank");
				break;
			case 18:
				target = Bukkit.createInventory(owner, 27, "Bank");
				break;
			case 27:
				target = Bukkit.createInventory(owner, 36, "Bank");
				break;
			case 36:
				target = Bukkit.createInventory(owner, 45, "Bank");
				break;
			case 45:
				target = Bukkit.createInventory(owner, 54, "Bank");
				break;
		}
		ItemStack gold = source.getItem(source.getSize() - 1);
		source.remove(source.getItem(source.getSize() - 1)); //Remove the gold withdraw / deposit item so we can put it in the last slot of the new inventory
		ItemStack[] contents = source.getContents();
		for(int i = 0; i < contents.length; i++)	{ //Move the contents from the old inventory to the new one.
			target.setItem(i, contents[i]);
		}
		target.setItem(target.getSize() - 1, gold);
		return target;
	}
	
	private Inventory generateEmptyBank(InventoryHolder owner)	{
		Inventory i = Bukkit.createInventory(owner, 9, "Bank");
		ItemStack item = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "0 Gold");
		item.setItemMeta(meta);
		i.setItem(8, item);
		return i;
	}
	
	@EventHandler
	public void onVehicleMove(VehicleMoveEvent event) {
	    Minecart vehicle = (Minecart) event.getVehicle();
	    Player player = (Player) vehicle.getPassenger();
	    Vector playerVector = player.getLocation().getDirection();
	   
	    vehicle.setPassenger(player);
	    vehicle.setCustomName(player + "'s Minecart");
	   
	    double x = Math.round(playerVector.getX());
	    double z = Math.round(playerVector.getZ());
	    
	    Vector velocity = new Vector(x, 0, z);
	    
	    Vector velocityMod = new Vector(1, 0, 1);
	    
	    vehicle.setDerailedVelocityMod(velocityMod);
	    vehicle.setVelocity(velocity);
	}
}