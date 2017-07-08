package com.aegeus.game.social;

import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.util.Util;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Silvre on 7/7/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class Party {
    private LinkedList<AgPlayer> members = new LinkedList<>();

    public Party(AgPlayer... members)   {
        this.members.addAll(Arrays.asList(members));
        update();
    }

    public Party(AgPlayer p)    {
        members.add(p);
        update();
    }

    public void promote(AgPlayer p)   {
        if(members.contains(p)) {
            members.remove(p);
            members.addFirst(p);
        }
        update();
    }

    public void addPlayer(AgPlayer p)   {
        if(!members.contains(p)) members.offer(p);
        update();
    }

    public boolean hasPlayer(AgPlayer p)   {
        return members.contains(p);
    }

    public AgPlayer getLeader() {
        return members.peek();
    }

    /**
     * @param p
     * @return True if the removed player was the leader
     */
    public boolean removePlayer(AgPlayer p)    {
        List<Player> list = members.stream().map(AgPlayer::getPlayer).collect(Collectors.toList());
        for(Player ap : list) GlowAPI.setGlowing(ap, false, p.getPlayer());
        GlowAPI.setGlowing(p.getPlayer(), false, list);
        if(members.peek().equals(p))    {
            members.remove(p);
            update();
            return true;
        }
        members.remove(p);
        update();
        for(Player ap : members.stream().map(AgPlayer::getPlayer).collect(Collectors.toList()))   {
            ap.sendMessage(Util.colorCodes("&a&l" + p.getPlayer().getName() + "&r&a has left the party."));
        }
        return false;
    }

    public void update()    {
        List<Player> list = members.stream().map(AgPlayer::getPlayer).collect(Collectors.toList());
        for(Player p : list) GlowAPI.setGlowing(p, GlowAPI.Color.PURPLE, list);
    }
}
