package com.aegeus.game.item.tool;

public enum Rune {
	FROSTBITE("Frostbite", "Increases the duration of slowing effects by 20%.");

	private final String name;
	private final String description;

	Rune(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return getName();
	}
}
