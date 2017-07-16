package com.aegeus.game;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.*;

/**
 * Created by Silvre on 7/14/2017.
 */
public class WorldManager {
    private Set<World> worlds = Collections.synchronizedSet(new HashSet<>());
    private static WorldManager instance;

    public static WorldManager getInstance() {
        return instance != null ? instance : (instance = new WorldManager());
    }

    public void initialize()    {
        worlds.addAll(Aegeus.getInstance().getServer().getWorlds());
    }

    public Set<World> getWorlds()  {
        return worlds;
    }

    public World getWorldFromName(String name)  {
        for(World w : worlds)
            if(w.getName().equals(name)) return w;
        return null;
    }

    public boolean addWorld(World w)    {
        return worlds.add(w);
    }

    /**
     * This will actually delete the world.
     * @param
     * @return
     */
    public boolean removeWorld(File path) {
        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) removeWorld(file);
            else file.delete();
        }
        return path.delete();
    }

    public void removeWorld(World w)    {
        Bukkit.unloadWorld(w, true);
        removeWorld(w.getWorldFolder());
    }
    public void deleteDungeons()    {
        List<World> deleted = new ArrayList<>();
        for(World w : worlds)   {
            if(w.getName().contains("dungeon")) {
                Aegeus.getInstance().getServer().unloadWorld(w, false);
                removeWorld(w.getWorldFolder());
                deleted.add(w);
            }
        }
        for(World w : deleted)
            worlds.remove(w);
    }
}
