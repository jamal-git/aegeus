package com.aegeus.aegeus.bot;

import java.util.logging.Level;

import com.aegeus.aegeus.Aegeus;

import net.md_5.bungee.api.ChatColor;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

public class AegeusBot {
	protected static IDiscordClient client;
	protected static final String TRADE_CHANNEL = "206600326659309570";
	private static final String TOKEN = "MjE5MzA4NjgzOTMzMzg0NzA0.CqSW0w.xbCYVfaZuVN3yUw5Nyh_JyazIhU";
	
	public AegeusBot() throws DiscordException	{
		client = new ClientBuilder().withToken(TOKEN).build();
		client.getDispatcher().registerListener(new EventHandler());
		client.getDispatcher().registerListener(this);
		client.login();
		Aegeus.log.log(Level.INFO, "Logging in Aegeus Bot...");
	}
	
	
	public void sendTradeMessage(String message)	{
		IChannel trade = client.getChannelByID(TRADE_CHANNEL);
		RequestBuffer.request(() ->	{
			try {
				trade.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
	@EventSubscriber
	public void onReady(ReadyEvent e)	{
		Aegeus.log.log(Level.INFO, ChatColor.AQUA + "AEGEUS BOT INITIALIZED.");
	}
}
