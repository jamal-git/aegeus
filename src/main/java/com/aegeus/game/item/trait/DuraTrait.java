package com.aegeus.game.item.trait;

import com.aegeus.game.item.ItemUtils;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

public interface DuraTrait extends Trait {
    default int getDura() {
        return ItemUtils.getNBTTag(getItem()).getInt("dura");
    }

    default void setDura(int dura) {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        tag.setInt("dura", dura);
        setItem(ItemUtils.setNBTTag(getItem(), tag));
    }

    int getMaxDura();

    /**
     * Displays custom dura on the item in reference to the max dura of its vanilla material.
     *
     * @return The dura to display.
     */
    default int display() {
        return (getDura() / getMaxDura()) * (getItem().getType().getMaxDurability() - 1);
    }
}
