package com.aegeus.game.commands;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.social.Party;
import com.aegeus.game.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Silvre on 7/7/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class CommandParty implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;
        Player play = (Player) commandSender;
        AgPlayer agplayer = Aegeus.getInstance().getPlayer(play);
        if(args[0].equalsIgnoreCase("create") && args.length == 1)   {
            if(agplayer.getParty() == null)   {
                agplayer.setParty(new Party(agplayer));
                play.sendMessage(Util.colorCodes("&aYou created a party.  Use &l/p invite&r&a to invite players."));
                return true;
            }
            else    {
                play.sendMessage(Util.colorCodes("&cYou are already in a party!"));
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("invite") && args.length == 2)  {
            Player p;
            if((p = Aegeus.getInstance().getServer().getPlayer(args[1])) == null)   {
                play.sendMessage(Util.colorCodes("&7That player does not exist or is offline!"));
                return true;
            }
            AgPlayer invitee = Aegeus.getInstance().getPlayer(p);
            if(invitee.getInvitedParty() == null) {
                if (agplayer.getParty() == null) agplayer.setParty(new Party(agplayer));
                Party host = agplayer.getParty();
                invitee.setInvitedParty(host);
                p.sendMessage(Util.colorCodes("&a&l" + play.getDisplayName() + "&r&a has invited you to their party. Use &l/p accept&r&a to join their party."));
                play.sendMessage(Util.colorCodes("&aYou have invited &l" + p.getDisplayName() + "&r&a to join your party."));
                Aegeus.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Aegeus.getInstance(), () -> {
                    if (invitee.getInvitedParty().equals(host)) {
                        invitee.setInvitedParty(null);
                        p.sendMessage(Util.colorCodes("&cThe party invite has &nexpired&r&c."));
                    }
                }, 1200);
            }
            else if(agplayer.getParty().equals(invitee.getInvitedParty()))  play.sendMessage(Util.colorCodes("&cYou already invited this player to the party!"));
            else if(!agplayer.getParty().equals(invitee.getInvitedParty())) play.sendMessage(Util.colorCodes("&cThat player has already been invited to another party!"));
            else    play.sendMessage(Util.colorCodes("&cThat player is already in a party!"));
            return true;
        }
        else if(args[0].equalsIgnoreCase("accept") && args.length == 1) {
            if(agplayer.getInvitedParty() == null)  {
                play.sendMessage(Util.colorCodes("&7You don't have a pending party invite!"));
            }
            else if(agplayer.getParty() != null) {
                play.sendMessage(Util.colorCodes("&cYou are already in a party!"));
            }
            else if(agplayer.getInvitedParty().hasPlayer(agplayer)) {
                play.sendMessage(Util.colorCodes("&cYou are already in that party!"));
            }
            else    {
                Party party = agplayer.getInvitedParty();
                agplayer.setParty(party);
                party.addPlayer(agplayer);
                agplayer.setInvitedParty(null);
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("kick") && args.length == 2)   {
            if(Aegeus.getInstance().getServer().getPlayer(args[1]) != null) {
                AgPlayer kickedfag = Aegeus.getInstance().getPlayer(Aegeus.getInstance().getServer().getPlayer(args[1]));
                if(agplayer.getParty().hasPlayer(kickedfag))   {
                    agplayer.getParty().removePlayer(kickedfag);
                    kickedfag.setParty(null);
                    kickedfag.getPlayer().sendMessage(Util.colorCodes("&c&lYou have been kicked from the party."));
                }
                else play.sendMessage(Util.colorCodes("&cThat player is not in your party!"));
            }
            return true;
        }
        return false;
    }
}
