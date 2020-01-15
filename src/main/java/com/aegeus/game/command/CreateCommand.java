package com.aegeus.game.command;

import com.aegeus.game.item.wrapper.ArmorItem;
import com.aegeus.game.item.wrapper.WeaponItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// TODO: Create command with custom parameters
public class CreateCommand implements Executor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return deny(sender, NOT_PLAYER);

        Player player = (Player) sender;

        WeaponItem weapon = new WeaponItem(Material.DIAMOND_SWORD);
        weapon.getDamage().set(60, 100);
        weapon.setRarity(1.0f);

        ArmorItem armor = new ArmorItem(Material.DIAMOND_CHESTPLATE);
        armor.setHealth(1000);
        armor.setRarity(0.5f);

        player.getInventory().addItem(weapon.build(), armor.build());

        return allow(sender, "&adone");
    }
}
