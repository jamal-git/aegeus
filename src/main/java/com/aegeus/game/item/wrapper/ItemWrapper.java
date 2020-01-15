package com.aegeus.game.item.wrapper;

import com.aegeus.game.item.ItemUtils;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemWrapper {
    private ItemStack item;
    private String identity;

    public ItemWrapper(ItemStack item, String identity) {
        this.item = item;
        this.identity = identity;
    }

    public ItemWrapper(Material material, String identity) {
        this(new ItemStack(material), identity);
    }

    public final ItemStack getItem() {
        return item;
    }

    public final void setItem(ItemStack item) {
        this.item = item;
    }

    /**
     * Gets the game identity for the item.
     * The identity is used to load the item again in the future.
     *
     * @return The item's identity.
     */
    public final String getIdentity() {
        return identity;
    }

    /**
     * Loads the NBT information of the item.
     *
     * @return The updated item.
     */
    public ItemWrapper load() {
        NBTTagCompound tag = ItemUtils.getNBTTag(item);
        identity = tag.getString("identity");
        return this;
    }

    /**
     * Saves the NBT information of the item.
     *
     * @return The updated item.
     */
    public ItemWrapper save() {
        NBTTagCompound tag = ItemUtils.getNBTTag(item);
        tag.setString("identity", getIdentity());
        setItem(ItemUtils.setNBTTag(item, tag));
        return this;
    }

    /**
     * Saves the item's NBT information, then generates a name and lore.
     *
     * @return The updated item.
     */
    public final ItemStack build() {
        save();

        ItemMeta meta = item.getItemMeta();
        meta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
        meta.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        String name = buildName();
        if (!name.isEmpty()) ItemUtils.setName(item, name);

        List<String> lore = buildLore();
        if (!lore.isEmpty()) ItemUtils.setLore(item, lore);

        return item;
    }

    /**
     * Generate a name for the item for use when building.
     *
     * @return The generated name.
     */
    public String buildName() {
        return "";
    }

    /**
     * Generates lore for the item for use when building.
     *
     * @return The generated lore.
     */
    public List<String> buildLore() {
        return new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }

    @Override
    public String toString() {
        return item.toString();
    }
}
