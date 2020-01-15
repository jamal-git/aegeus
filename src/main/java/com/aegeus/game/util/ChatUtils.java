package com.aegeus.game.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static void nearby(Player sender, String content) {
        String message = Util.colorCodes(content);
        sender.sendMessage(message);
        sender.getNearbyEntities(60, 60, 60).forEach(p -> p.sendMessage(message));
    }

    public static void world(World world, String content) {
        world.getPlayers().forEach(p -> p.sendMessage(Util.colorCodes(content)));
    }

    public static void server(String content) {
        Bukkit.getServer().getOnlinePlayers().forEach(p -> p.sendMessage(Util.colorCodes(content)));
    }
}
