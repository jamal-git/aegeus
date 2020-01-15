package com.aegeus.game.command;

import com.aegeus.game.util.ChatUtils;
import com.aegeus.game.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RollCommand implements Executor {
    private static final int MAX_DEFAULT = 100;
    private static final int MAX_MAX = 100000;
    private static final int MAX_MIN = 1;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return deny(sender, NOT_PLAYER);

        int max = MAX_DEFAULT;

        if (args.length >= 1) try {
            max = Integer.parseInt(args[0]);
            if (max > MAX_MAX)
                return deny(sender, "The max cannot be greater than " + MAX_MAX + ".");
            else if (max < MAX_MIN)
                return deny(sender, "The max cannot be less than " + MAX_MIN + ".");
        } catch (NumberFormatException error) {
            return deny(sender, "&cInvalid max roll amount.");
        }

        ChatUtils.nearby((Player) sender, "&7" + sender.getName() + " rolled a &f" + Util.nextInt(0, max) + "&7 out of &f" + max + "&7.");

        return true;
    }
}
