package com.aegeus.game.commands.social;

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
 */
public class CommandParty implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;
        Player play = (Player) commandSender;
        AgPlayer agplayer = Aegeus.getInstance().getPlayer(play);
        if(args[0].equalsIgnoreCase("create") && args.length == 1)   {
            //Player wants to create a party.
            //The conditions to create a party is that the player must not already be in a party.
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
            //Invite a player to your current party.
            //If the inviter is not currently in a party, make one and then invite the invitee.
            Player p;
            if((p = Aegeus.getInstance().getServer().getPlayer(args[1])) == null || !p.isOnline())   {
                //The player does not exist or is offline
                play.sendMessage(Util.colorCodes("&7That player does not exist or is offline!"));
                return true;
            }
            //Player exists and is currently online, inviting them.
            AgPlayer invitee = Aegeus.getInstance().getPlayer(p);
            if(invitee.getInvitedParty() == null) {
                //Inviter is not the leader of the party, cannot invite other players.
                if (agplayer.getParty() == null) agplayer.setParty(new Party(agplayer));
                Party host = agplayer.getParty();
                if(!host.getLeader().equals(agplayer))  {
                    play.sendMessage(Util.colorCodes("&cYou are not the leader of the party!"));
                }
                else {
                    //Invite the invitee to the party.  There currently is no expiration timer on party invites.
                    //The invitee will have to decline if they want to join another player's party.
                    invitee.setInvitedParty(host);
                    p.sendMessage(Util.colorCodes("&a&l" + play.getDisplayName() + "&r&a has invited you to their party. Use &l/p accept&r&a to join their party."));
                    play.sendMessage(Util.colorCodes("&aYou have invited &l" + p.getDisplayName() + "&r&a to join your party."));
                }
            }
            //The person has already been invited to the party, don't spam.
            else if(agplayer.getParty().equals(invitee.getInvitedParty()))  play.sendMessage(Util.colorCodes("&cYou already invited this player to the party!"));
            //The person has been invited to another party, they will have to decline to accept more party invites.
            else if(!agplayer.getParty().equals(invitee.getInvitedParty())) play.sendMessage(Util.colorCodes("&cThat player has already been invited to another party!"));
            //The player is already in a party, cannot invite.
            else if(invitee.getParty() != null)   play.sendMessage(Util.colorCodes("&cThat player is already in a party!"));
            return true;
        }
        else if(args[0].equalsIgnoreCase("accept") && args.length == 1) {
            //Accept a pending party invite.
            if(agplayer.getInvitedParty() == null)  {
                //Player does not have any pending party invites
                play.sendMessage(Util.colorCodes("&7You don't have a pending party invite!"));
            }
            else if(agplayer.getParty() != null) {
                //The player is already in a party
                play.sendMessage(Util.colorCodes("&cYou are already in a party!"));
            }
            else if(agplayer.getInvitedParty().hasPlayer(agplayer)) {
                //The player is already in the invited party.
                play.sendMessage(Util.colorCodes("&cYou are already in that party!"));
            }
            else    {
                Party party = agplayer.getInvitedParty();
                agplayer.setParty(party);
                party.addPlayer(agplayer);
                play.sendMessage(Util.colorCodes("&7You joined &a&l" + party.getLeader().getPlayer().getDisplayName() + "&7's party!"));
            }
            agplayer.setInvitedParty(null);
            return true;
        }
        else if(args[0].equalsIgnoreCase("kick") && args.length == 2)   {
            //Kick a player from the party.
            if(Aegeus.getInstance().getServer().getPlayer(args[1]) != null) {
                AgPlayer kickedfag = Aegeus.getInstance().getPlayer(Aegeus.getInstance().getServer().getPlayer(args[1]));
                if(!agplayer.getParty().getLeader().equals(agplayer))   {
                    //Attempting to kick player but is not the leader.
                    play.sendMessage(Util.colorCodes("&cYou are not the leader of your party!"));
                }
                if(agplayer.getParty().hasPlayer(kickedfag))   {
                    //Kick that player lol
                    agplayer.getParty().removePlayer(kickedfag);
                    kickedfag.setParty(null);
                    kickedfag.getPlayer().sendMessage(Util.colorCodes("&c&lYou have been kicked from the party."));
                }
                //The player is not in the party.
                else play.sendMessage(Util.colorCodes("&cThat player is not in your party!"));
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("decline") && args.length == 1)    {
            //Decline a pending party invite
            if(agplayer.getInvitedParty() == null)  {
                //Doesnt have any party invites
                play.sendMessage(Util.colorCodes("&cYou dont have a pending party invite!"));
            }
            else    {
                //Decline the party invite
                agplayer.getInvitedParty().getLeader().getPlayer().sendMessage("&c&l" + agplayer.getPlayer().getDisplayName() + "&r&c has declined the party invite.");
                play.sendMessage(Util.colorCodes("&7You decline the party invite."));
                agplayer.setInvitedParty(null);
            }
        }
        else if(args[0].equalsIgnoreCase("quit") && args.length == 1)   {
            //Leave the current party
            if(agplayer.getParty() == null) {
                //The player isnt in any parties.
                play.sendMessage(Util.colorCodes("&cYou are currently not in a party!"));
            }
            else    {
                //Leave the party. Bye
                agplayer.getParty().removePlayer(agplayer);
                agplayer.setParty(null);
            }
        }
        return false;
    }
}