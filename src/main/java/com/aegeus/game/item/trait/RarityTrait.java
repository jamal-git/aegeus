package com.aegeus.game.item.trait;

import com.aegeus.game.item.ItemUtils;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

import java.util.Collections;
import java.util.List;

import static com.aegeus.game.util.Rarity.*;

public interface RarityTrait extends Trait {
    default float getRarity() {
        return ItemUtils.getNBTTag(getItem()).getFloat("rarity");
    }

    default void setRarity(float rarity) {
        NBTTagCompound tag = ItemUtils.getNBTTag(getItem());
        tag.setFloat("rarity", rarity);
        setItem(ItemUtils.setNBTTag(getItem(), tag));
    }

    @Override
    default List<String> buildLore() {
        float rarity = getRarity();
        if (rarity > RARE + UNCOMMON + COMMON)
            return Collections.singletonList("&e&oUnique");
        else if (rarity > UNCOMMON + COMMON)
            return Collections.singletonList("&b&oRare");
        else if (rarity > COMMON)
            return Collections.singletonList("&a&oUncommon");
        else
            return Collections.singletonList("&8&oCommon");
    }
}
