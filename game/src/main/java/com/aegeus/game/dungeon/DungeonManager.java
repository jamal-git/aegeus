package com.aegeus.game.dungeon;

import com.aegeus.game.social.Party;
import org.bukkit.World;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DungeonManager {
	private static DungeonManager instance = null;
	private Set<Dungeon> dungeons = Collections.synchronizedSet(new HashSet<>());

	public static DungeonManager getInstance() {
		return instance != null ? instance : (instance = new DungeonManager());
	}

	public Dungeon registerDungeon(Dungeon d) {
		dungeons.add(d);
		return d;
	}

	public Dungeon removeDungeon(Dungeon d) {
		dungeons.remove(d);
		return d;
	}

	public Dungeon getDungeon(Party p) {
		for (Dungeon d : dungeons)
			if (d.getParty().equals(p)) return d;
		return null;
	}

	public int getNumberOfDungeons() {
		return dungeons.size();
	}

	public Dungeon getDungeonFromWorld(World w) {
		for (Dungeon d : dungeons) {
			if (d.getWorld().getName().equals(w.getName())) return d;
		}
		return null;
	}
}
