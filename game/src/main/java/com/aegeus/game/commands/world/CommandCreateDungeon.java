package com.aegeus.game.commands.world;

import com.aegeus.game.dungeon.Dungeon;
import com.aegeus.game.util.Util;
import com.aegeus.game.util.exceptions.DungeonLoadingException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Silvre on 7/11/2017.
 */
public class CommandCreateDungeon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("aegeus.world"))   {
            sender.sendMessage(Util.colorCodes("&cYou do not have sufficient permissions to execute this command!"));
            return false;
        }
        if(sender instanceof ConsoleCommandSender)  {
            sender.sendMessage(Util.colorCodes("&cYou can't execute this command through the console! Get into the game!"));
            return false;
        }
        Player p = (Player) sender;
        if(args.length != 2)    {
            p.sendMessage(Util.colorCodes("&cInvalid arguments! Use /dungeon <directory> <length>"));
            return false;
        }
        try {
            Dungeon d = new Dungeon(args[0], Integer.valueOf(args[1]));
        } catch (DungeonLoadingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
