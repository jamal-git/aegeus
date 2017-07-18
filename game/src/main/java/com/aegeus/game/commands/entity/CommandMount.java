package com.aegeus.game.commands.entity;

import com.aegeus.game.util.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Silvre on 7/17/2017.
 */
public class CommandMount implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        Horse h = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
        h.setVariant(Horse.Variant.HORSE);
        h.setColor(Horse.Color.WHITE);
        h.setStyle(Horse.Style.NONE);
        h.setJumpStrength(1.3);
        h.setCustomName(Util.colorCodes("&6Royal Steed"));
        h.setCustomNameVisible(true);
        h.setTamed(true);
        h.setOwner(p);
        h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
        h.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING, 1));
        Util.setSpeed(h, 0.420);
        h.setPassenger(p);
        h.setMaxHealth(40);
        h.setHealth(40);
        h.setAdult();
        return true;
    }
}
