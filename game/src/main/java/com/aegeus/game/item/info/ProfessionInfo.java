package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silvre on 7/1/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public interface ProfessionInfo extends ItemInfo {
    static <T extends AgItem & ProfessionInfo> void impo(T t) {
        NBTTagCompound info = t.getAegeusInfo();
        t.setLevel(info.hasKey("level") ? info.getInt("level") : 0);
        t.setXp(info.hasKey("xp") ? info.getInt("xp") : 0);

    }

    static <T extends AgItem & ProfessionInfo> void store(T t) {
        NBTTagCompound info = t.getAegeusInfo();
        info.set("level", new NBTTagInt(t.getLevel()));
        info.set("xp", new NBTTagInt(t.getXp()));
        t.setAegeusInfo(info);
    }

    @Override
    default List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        lore.add(Util.colorCodes("&7Level: " + ProfessionTier.getTierByLevel(getLevel()).getColor() + getLevel()));
        lore.add(Util.colorCodes("&7" + getXp() + " / " + getRequiredXp() + "(" + new DecimalFormat("####0.00%").format((getXp() + 0.0) / getRequiredXp()) + ")"));
        return lore;
    }

    int getLevel();

    void setLevel(int level);

    default void addLevel(int i) {
        setLevel(getLevel() + i);
    }

    int getXp();

    void setXp(int xp);

    default void addXp(int i) {
        setXp(getXp() + i);
    }

    int getRequiredXp();

}
