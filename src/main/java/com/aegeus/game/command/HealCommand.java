package com.aegeus.game.command;

import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements Executor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return deny(sender, NOT_PLAYER);

        Player target = (Player) sender;

        if (args.length >= 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null)
                return deny(sender, "&cInvalid target.");
        }

        Util.heal(target);
        target.setSaturation(20);

        return allow(sender, "Healed " + target.getName() + ".");
    }
}
