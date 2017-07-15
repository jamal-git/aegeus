package com.aegeus.game.dungeon;

import com.aegeus.game.social.Party;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silvre on 7/14/2017.
 */
public class DungeonManager {
    private Set<Dungeon> dungeons = Collections.synchronizedSet(new HashSet<>());
    private static DungeonManager instance = null;

    public static DungeonManager getInstance() {
        return instance != null ? instance : (instance = new DungeonManager());
    }

    public Dungeon registerDungeon(Dungeon d)  {
        dungeons.add(d);
        return d;
    }

    public Dungeon removeDungeon(Dungeon d)    {
        dungeons.remove(d);
        return d;
    }

    public Dungeon getDungeon(Party p)  {
        for(Dungeon d : dungeons)
            if(d.getParty().equals(p)) return d;
        return null;
    }

    public int numberOfDungeons()   {
        return dungeons.size();
    }
}
