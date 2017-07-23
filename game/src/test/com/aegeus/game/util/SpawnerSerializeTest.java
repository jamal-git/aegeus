package com.aegeus.game.util;

import com.aegeus.game.entity.Spawner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.junit.Assert;
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
@PrepareForTest(Bukkit.class)
public class SpawnerSerializeTest {
    private static Gson g = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Spawner.class, new SpawnerDeserializer())
            .registerTypeAdapter(Spawner.class, new SpawnerSerializer())
            .create();
    private static World w = Mockito.spy(World.class);
    private static Spawner s = new Spawner(new Location(w, 0, 0, 0));

    static {
        when(w.getName()).thenReturn("world");
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
