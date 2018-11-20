package com.aegeus.game.command;

import com.aegeus.game.Aegeus;
import com.aegeus.game.command.util.Executor;
import com.aegeus.game.item.Items;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateCommand implements Executor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		Player player = (Player) sender;
		LivingEntity e = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		Aegeus.getInstance().getEntities().getLiving(e).getDrops().add(new ItemStack(Material.BOAT));

		((Player) sender).getInventory().addItem(Items.TRAP.get());
		return true;
	}
}
