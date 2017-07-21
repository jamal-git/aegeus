package com.aegeus.game.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Silvre on 7/20/2017.
 */
public class InventoryMenuManager {
    private static Set<InventoryBuilder> inventories = new HashSet<>();

    public static void addInventory(InventoryBuilder i)   {
        inventories.add(i);
    }

    public static void removeInventory(InventoryBuilder b)  {
        inventories.remove(b);
    }
}
