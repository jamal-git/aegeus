package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.ProfessionTier;
import com.aegeus.game.item.info.ProfessionInfo;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagFloat;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Silvre on 6/24/2017.
 * Project: aegeus
 * If you are reading this - you can read this
 */
public class Pickaxe extends AgItem implements ProfessionInfo {

    private static final double CONSTANT = 1.114;

    private int level;
    private int xp;
    private int requiredxp;

    private ProfessionTier tier;
    private boolean tieredUp;

    private float miningSuccess;
    private float denseFind;
    private int denseMultiplier;
    private float doubleOre;
    private float tripleOre;
    private float gemFind;
    private float durability;

    public Pickaxe(ItemStack stack) {
        super(stack);
    }

    public Pickaxe()    {
        this(1);
    }

    public Pickaxe(int level)    {
        super(ProfessionTier.getTierByLevel(level).getPickaxeMaterial());
        this.level = level;
        tier = ProfessionTier.getTierByLevel(level);
        xp = 0;
        requiredxp = getXPRequired(level + 1);
    }

    public int getTierAsInt()    {
        return tier.ordinal() + 1;
    }

    public ProfessionTier getTier()   {
        return tier;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        setMaterial(ProfessionTier.getTierByLevel(level).getPickaxeMaterial());
        this.level = level;
        tier = ProfessionTier.getTierByLevel(level);
        requiredxp = getXPRequired(level + 1);
    }

    @Override
    public int getXp() {
        return xp;
    }

    /**
     * DON'T USE THIS.
     * @param xp
     */
    @Override
    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public int getRequiredXp() {
        return requiredxp;
    }

    @Override
    public void setRequiredXp(int requiredXp) {
        //dont use this. sorry.
    }

    public int getXPRequired()  {
        return requiredxp;
    }

    @Override
    public void impo() {
        ProfessionInfo.impo(this);

        NBTTagCompound info = getAegeusInfo();
        setMiningSuccess(info.hasKey("miningSuccess") ? info.getFloat("miningSuccess") : 0);
        setDenseFind(info.hasKey("denseFind") ? info.getFloat("denseFind") : 0);
        setDenseMultiplier(info.hasKey("denseMultiplier") ? info.getInt("denseMultiplier") : 0);
        setDoubleOre(info.hasKey("doubleOre") ? info.getFloat("doubleOre") : 0);
        setTripleOre(info.hasKey("tripleOre") ? info.getFloat("tripleOre") : 0);
        setGemFind(info.hasKey("gemFind") ? info.getFloat("gemFind") : 0);
        setDurability(info.hasKey("durability") ? info.getFloat("durability") : 0);

    }

    @Override
    public void store() {
        ProfessionInfo.store(this);

        NBTTagCompound info = getAegeusInfo();
        info.set("type", new NBTTagString("pickaxe"));
        info.set("miningSuccess", new NBTTagFloat(getMiningSuccess()));
        info.set("denseFind", new NBTTagFloat(getDenseFind()));
        info.set("denseMultiplier", new NBTTagInt(getDenseMultiplier()));
        info.set("doubleOre", new NBTTagFloat(getDoubleOre()));
        info.set("tripleOre", new NBTTagFloat(getTripleOre()));
        info.set("gemFind", new NBTTagFloat(getGemFind()));
        info.set("durability", new NBTTagFloat(getDurability()));
        setAegeusInfo(info);
    }

    @Override
    public List<String> buildLore() {
        List<String> lore = ProfessionInfo.super.buildLore();
        if(miningSuccess > 0) lore.add(Util.colorCodes("&cMINING SUCCESS: +" + miningSuccess + "%"));
        if(denseFind > 0) lore.add(Util.colorCodes("&cDENSE FIND: +" + denseFind + "%"));
        if(denseMultiplier > 0) lore.add(Util.colorCodes("&cDENSE MULTIPLIER: " + denseMultiplier + "x"));
        if(doubleOre > 0) lore.add(Util.colorCodes("&cDOUBLE ORE: +" + doubleOre + "%"));
        if(tripleOre > 0) lore.add(Util.colorCodes("&cTRIPLE ORE: +" + tripleOre + "%"));
        if(gemFind > 0) lore.add(Util.colorCodes("&cGEM FIND: +" + gemFind + "%"));
        if(durability > 0) lore.add(Util.colorCodes("&cDURABILITY: +" + durability + "%"));
        return lore;
    }

    @Override
    public boolean verify() {
        NBTTagCompound info = getAegeusInfo();
        return info.hasKey("type") && info.getString("type").equalsIgnoreCase("pickaxe");
    }

    @Override
    public ItemStack build()    {
        store();
        setLore(buildLore());
        setName(tier.getColor() + tier.getPickaxeName());
        return super.build();
    }

    public boolean addExp(int i)   {
        xp += i;
        if(xp >= requiredxp)    {
            xp -= requiredxp;
            requiredxp = getXPRequired(++level + 1);
            if(level != 100 && level % 20 == 0) {
                tier = tier.next();
                setMaterial(tier.getPickaxeMaterial());
                //todo implement stats.
                tieredUp = true;
            }
            return true;
        }
        return false;
    }

    public boolean checkForNextTier() {
        return !(tieredUp ^= true);
    }

    public static int getXPRequired(int targetLevel)    {
        double xp = Math.pow(CONSTANT, targetLevel) * 100;
        if(targetLevel >= 80)   {
            xp += 50000 * (targetLevel - 80);
        }
        if(targetLevel >= 60)   {
            xp += 10000 * (targetLevel - 60);
        }
        if(targetLevel >= 40)   {
            xp += 5000 * (targetLevel - 40);
        }
        if(targetLevel >= 20)
            xp += 1200 * (targetLevel - 20);
        xp += 100 * targetLevel;
        return (int) Math.round(xp);
    }

    /*
     * Encapsulations
     */

    public float getMiningSuccess() {
        return miningSuccess;
    }

    public void setMiningSuccess(float miningSuccess) {
        this.miningSuccess = miningSuccess;
    }

    public float getDenseFind() {
        return denseFind;
    }

    public void setDenseFind(float denseFind) {
        this.denseFind = denseFind;
    }

    public int getDenseMultiplier() {
        return denseMultiplier;
    }

    public void setDenseMultiplier(int denseMultiplier) {
        this.denseMultiplier = denseMultiplier;
    }

    public float getDoubleOre() {
        return doubleOre;
    }

    public void setDoubleOre(float doubleOre) {
        this.doubleOre = doubleOre;
    }

    public float getTripleOre() {
        return tripleOre;
    }

    public void setTripleOre(float tripleOre) {
        this.tripleOre = tripleOre;
    }

    public float getGemFind() {
        return gemFind;
    }

    public void setGemFind(float gemFind) {
        this.gemFind = gemFind;
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float durability) {
        this.durability = durability;
    }
}