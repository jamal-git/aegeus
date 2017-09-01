package com.aegeus.game.commands.social;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.social.Party;
import com.aegeus.game.social.PartyManager;
import com.aegeus.game.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Silvre on 7/7/2017.
 */
public class CommandParty implements CommandExecutor {
    private PartyManager parties = PartyManager.getInstance();
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		if (!(commandSender instanceof Player)) return false;
		Player play = (Player) commandSender;
		AgPlayer agplayer = Aegeus.getInstance().getPlayer(play);
		if (args[0].equalsIgnoreCase("create") && args.length == 1) {
			if(parties.isInParty(agplayer)) {
			    agplayer.sendMessage(Util.colorCodes("&cYou are already in a party -- Leave your current one to make a new party."));
            }
            else    {
			    parties.registerParty(new Party(agplayer));
			    agplayer.sendMessage(Util.colorCodes("&aYou have created a party.  Use \"&l/p invite <player>&r&a\" to invite them to your party."));
            }
		} else if (args[0].equalsIgnoreCase("invite") && args.length == 2) {
			//Invite a player to your current party.
			//If the inviter is not currently in a party, make one and then invite the invitee.
			Player p;
			if(!parties.isInParty(agplayer))    {
			    parties.registerParty(new Party(agplayer));
			    agplayer.sendMessage(Util.colorCodes("&aYou have created a party.  Use \"&l/p invite <player>&r&a\" to invite them to your party."));
            }
			if((p = Aegeus.getInstance().getServer().getPlayer(args[1])) == null || !p.isOnline())  {
			    agplayer.sendMessage(Util.colorCodes("&cThat player does not exist or is offline."));
            }
            else    {
			    AgPlayer invitee = Aegeus.getInstance().getPlayer(p);
			    parties.inviteToParty(agplayer, invitee);
            }
			return true;
		} else if (args[0].equalsIgnoreCase("accept") && args.length == 1) {
			parties.acceptInvite(agplayer);
		} else if (args[0].equalsIgnoreCase("kick") && args.length == 2) {
			//Kick a player from the party.
            Player p;
			if ((p = Aegeus.getInstance().getServer().getPlayer(args[1])) != null && p.isOnline()) {
				AgPlayer kickedfag = Aegeus.getInstance().getPlayer(p);
				parties.kickFromParty(agplayer, kickedfag);
			}
			else agplayer.sendMessage(Util.colorCodes("&cThat player does not exist or is offline."));
			return true;
		} else if (args[0].equalsIgnoreCase("decline") && args.length == 1) {
			parties.declineInvite(agplayer);
		} else if (args[0].equalsIgnoreCase("quit") && args.length == 1) {
			parties.leaveParty(agplayer);
		}
		return false;
	}
}