package com.aegeus.discord;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class EventHandler {
	
	@EventSubscriber
	public void onReady(ReadyEvent event) {
		System.out.println("CORIUS initialized.");
	}
	
	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) {
		
	}
}
