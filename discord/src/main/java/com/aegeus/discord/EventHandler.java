package com.aegeus.discord;

import com.aegeus.common.Common;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class EventHandler {
	
	@EventSubscriber
	public void onReady(ReadyEvent e) throws DiscordException {
		System.out.println("CORIUS initialized.");
		System.out.println("Invite to CORIUS: "
						 + "https://discordapp.com/oauth2/authorize?client_id="
						 + e.getClient().getApplicationClientID()
						 + "&scope=bot&permissions=0"
		);
	}
	
	@EventSubscriber
	public void onMessage(MessageReceivedEvent e) throws RateLimitException, DiscordException, MissingPermissionsException {
		IDiscordClient client = e.getClient();
		IMessage message = e.getMessage();
		if(message.getAuthor().isBot()) return;
		String content = message.getContent();
		
		if(content.startsWith(Corius.PREFIX)
				|| content.startsWith(client.getOurUser().mention(true))
				|| content.startsWith(client.getOurUser().mention(false))) {
			String[] args = content
					.replaceFirst(Corius.PREFIX, "")
					.replaceFirst(client.getOurUser().mention(true), "")
					.replaceFirst(client.getOurUser().mention(false), "")
					.split(" ");
			IChannel channel = message.getChannel();
			IUser user = message.getAuthor();
			
			if(args[0].equalsIgnoreCase("version")
					|| args[0].equalsIgnoreCase("ver")
					|| args[0].equalsIgnoreCase("v")
					|| args[0].equalsIgnoreCase("corius")
					|| args[0].equalsIgnoreCase("about")){
				MessageBuilder builder = new MessageBuilder(client).withChannel(channel);
				builder.appendContent("Patch: " + Common.PATCH);
				builder.appendContent(System.lineSeparator());
				builder.appendContent("Patch Note: " + Common.PATCH_NOTE);
				builder.send();
			}
			
		}
	}
	
}
