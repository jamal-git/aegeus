package com.aegeus.aegeus.game.stats;

import com.aegeus.aegeus.game.item.ItemArmor;

public interface Stats {
	public String getName();
	
	public default boolean hasHelmet(){
		return false;
	}
	public default boolean hasChestplate(){
		return false;
	}
	public default boolean hasLeggings(){
		return false;
	}
	public default boolean hasBoots(){
		return false;
	}
	
	public default ItemArmor getHelmet(){
		return null;
	}
	public default ItemArmor getChestplate(){
		return null;
	}
	public default ItemArmor getLeggings(){
		return null;
	}
	public default ItemArmor getBoots(){
		return null;
	}
	
}
