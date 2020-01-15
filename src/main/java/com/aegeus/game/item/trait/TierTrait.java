package com.aegeus.game.item.trait;

import com.aegeus.game.item.ItemUtils;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

public interface TierTrait extends Trait {
    default int getTier() {
        return ItemUtils.getNBTTag(getItem()).getInt("tier");
    }

    default void setTier(int tier) {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        tag.setInt("tier", tier);
        setItem(ItemUtils.setNBTTag(getItem(), tag));
    }

    @Override
    default String buildName() {
        int tier = getTier();
        switch (tier) {
            default:
                return "&f";
            case 2:
                return "&a";
            case 3:
                return "&b";
            case 4:
                return "&d";
            case 5:
                return "&e";
        }
    }
}
