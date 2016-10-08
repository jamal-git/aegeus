package com.aegeus.discord;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Corius {
	private static IDiscordClient client;
	public static final String TOKEN = "MjMxOTMzOTk1Mjk2ODgyNjg4.CtHk6Q.IaUSTT8w3oj8pgo0KEt0X4dSH6U";
	public static final String PREFIX = "!!";
	
	public static void main(String[] args) throws DiscordException {
		System.out.println("CORIUS is initializing...");
		
		client = new ClientBuilder().withToken(TOKEN).build();
		
		client.getDispatcher().registerListener(new EventHandler());
		client.login();
	}
	
	public static IDiscordClient getClient() {
		return client;
	}
	
}
