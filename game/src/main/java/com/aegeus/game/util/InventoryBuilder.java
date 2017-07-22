package com.aegeus.game.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Silvre on 7/18/2017.
 */
public class InventoryBuilder {
    private InventoryHolder owner;
    private Pair<ItemStack, InventoryHandler>[] items;
    private int size = 36;
    private String title;

    public InventoryBuilder(InventoryHolder owner, int size, String title)   {
        this.owner = owner;
        this.title = title;
        if(size % 9 != 0 || size > 108 || size < 0)
            throw new RuntimeException("Invalid inventory size!");
        this.size = size;
        items = new Pair[size];
    }

    public int numberOfItems()  {
        int count = 0;
        for(Pair p : items) {
            if(p != null)   {
                count++;
            }
        }
        return count;
    }

    public InventoryBuilder setItem(int slot, ItemStack itemStack)    {
        if(slot < size)   {
            items[slot] = new Pair<>(itemStack, null);
            return this;
        }
        else throw new RuntimeException("Invalid slot number.");
    }

    public InventoryBuilder setOnClick(int slot, InventoryHandler runnable) {
        if(items[slot] == null || runnable == null) throw new RuntimeException("Invalid slot or runnable");
        items[slot].setValue(runnable);
        return this;
    }

    public void click(int slot, InventoryClickEvent e) {
        if(slot < size && items[slot].getValue() != null)   {
            items[slot].getValue().run(e);
        }
    }

    public void show(Player p)    {
        Inventory i = Bukkit.getServer().createInventory(owner, size, title);
        for (int i1 = 0; i1 < items.length; i1++) {
            if(items[i1] != null) {
                i.setItem(i1, items[i1].getKey());
            }
        }
        InventoryMenuManager.addInventory(this);
        p.openInventory(i);
    }

    public Inventory getInventory() {
        Inventory i = Bukkit.getServer().createInventory(owner, size, title);
        for (int j = 0; j < items.length; j++) {
            if(items[j] != null)   {
                i.setItem(j, items[j].getKey());
            }
        }
        return i;
    }

    public class Pair<K, V> {
        private K k;
        private V v;

        public Pair(K k, V v)   {
            this.k = k;
            this.v = v;
        }

        public K getKey() {
            return k;
        }

        public V getValue() {
            return v;
        }

        public void setKey(K k) {
            this.k = k;
        }

        public void setValue(V v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Pair) && ((Pair) obj).getKey().equals(k) && ((Pair) obj).getValue().equals(v);
        }
    }

}