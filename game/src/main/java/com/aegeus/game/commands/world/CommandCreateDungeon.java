package com.aegeus.game.commands.world;

import com.aegeus.game.Aegeus;
import com.aegeus.game.WorldManager;
import com.aegeus.game.dungeon.Dungeon;
import com.aegeus.game.dungeon.DungeonManager;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.social.Party;
import com.aegeus.game.social.PartyManager;
import com.aegeus.game.util.Util;
import com.aegeus.game.util.exceptions.DungeonLoadingException;
import com.sk89q.worldedit.data.DataException;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

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
        if(args.length != 5)    {
            sender.sendMessage(Util.colorCodes("&cInvalid arguments! Use /dungeon <directory> <length> <array size> <number of segments> <schematic size>"));
            return false;
        }
        Player p = (Player) sender;
        AgPlayer player = Aegeus.getInstance().getPlayer(p);
        Party party = player.getParty();
        if(party == null)   {
            party = new Party(player);
            PartyManager.getInstance().registerParty(party);
        }
        p.sendMessage(Util.colorCodes("&7Generating dungeon..."));
        Aegeus.getInstance().getServer().getScheduler().runTaskAsynchronously(Aegeus.getInstance(), () -> {
            try {
                World w = new WorldCreator("dungeon" + WorldManager.getInstance().getWorlds().size()).environment(World.Environment.NORMAL).generateStructures(false).type(WorldType.FLAT).generatorSettings("1;0").createWorld();
                Dungeon d = DungeonManager.getInstance().registerDungeon(new Dungeon(player.getParty(), w.getSpawnLocation().add(0, 64, 0), args[0], Integer.valueOf(args[1]), w, Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])));
                WorldManager.getInstance().addWorld(w);
            } catch (DungeonLoadingException | IOException | DataException e) {
                e.printStackTrace();
            }
        });
        return true;
    }
}
