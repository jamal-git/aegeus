package com.aegeus.aegeus.game.chat;

public enum ChatChannel {
	LOCAL(0),
	GLOBAL(1),
	GUILD(2);
	
	int typeID;
	
	ChatChannel(int typeID){
		this.typeID = typeID;
	}
	
	public static ChatChannel fromTypeID(int typeID){
		switch(typeID){
			case 0:
				return LOCAL;
			case 1:
				return GLOBAL;
			case 2:
				return GUILD;
		}
		return null;
	}
	
}
