package com.aegeus.game.dungeon;

import com.aegeus.game.Aegeus;
import com.aegeus.game.util.exceptions.DungeonLoadingException;
import com.sk89q.worldedit.CuboidClipboard;

import java.io.File;
import java.util.List;

/**
 * Created by Silvre on 7/10/2017.
 */
public class Dungeon {
    private File directory;
    private List<CuboidClipboard> starts;
    private List<CuboidClipboard> straights;
    private List<CuboidClipboard> turns;
    private List<CuboidClipboard> trijunctions;
    private List<CuboidClipboard> quadjunctions;
    private List<CuboidClipboard> keys;

    public Dungeon(String directory) throws DungeonLoadingException {
        File temp = new File(Aegeus.getInstance().getDataFolder() + "/dungeons/" + directory + "/");
        if(!temp.exists() || !temp.isDirectory())   {
            throw new DungeonLoadingException("The directory selected does not exist or has been corrupted.");
        }
        else    {
            this.directory = temp;
        }
    }
}
