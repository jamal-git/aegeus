package com.aegeus.game.util.exceptions;

public class NoneNearbyException extends Exception {
	private static final long serialVersionUID = -6999149451642569982L;

	public NoneNearbyException() {
		super("There are no players nearby.");
	}
}
