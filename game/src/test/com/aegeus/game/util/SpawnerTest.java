package com.aegeus.game.util;

import com.aegeus.game.entity.Spawner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.when;

/**
 * Created by Silvre on 7/22/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class, Util.class, Spawner.class, Location.class})
public class SpawnerTest {
    private static Gson g = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Spawner.class, new SpawnerDeserializer())
            .registerTypeAdapter(Spawner.class, new SpawnerSerializer())
            .create();
    private static World w = Mockito.spy(World.class);
    private static Chunk chunk = Mockito.spy(Chunk.class);
    private static Location unmocked = new Location(w, 0, 0, 0);
    private static Spawner s = new Spawner(unmocked);

    @BeforeClass
    public static void setup() {
        when(w.getName()).thenReturn("world");
        when(w.getDifficulty()).thenReturn(Difficulty.NORMAL);
        when(unmocked.getChunk()).thenReturn(chunk);
    }

    @Test
    public void serializeTest() throws Exception {
        Assert.assertEquals(g.toJson(s), "{\n" +
                "  \"world\": \"world\",\n" +
                "  \"x\": 0.0,\n" +
                "  \"y\": 0.0,\n" +
                "  \"z\": 0.0,\n" +
                "  \"max\": 3,\n" +
                "  \"delay\": 1,\n" +
                "  \"list\": []\n" +
                "}");
    }

    @Test
    public void deserializeTest() throws Exception {
        Server server = Mockito.spy(Server.class);
        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getServer()).thenReturn(server);
        when(server.getWorld(Mockito.anyString())).thenReturn(w);
        Assert.assertEquals(s, g.fromJson("{\n" +
                "  \"world\": \"world\",\n" +
                "  \"x\": 0.0,\n" +
                "  \"y\": 0.0,\n" +
                "  \"z\": 0.0,\n" +
                "  \"max\": 3,\n" +
                "  \"delay\": 1,\n" +
                "  \"list\": []\n" +
                "}", Spawner.class));
    }
}