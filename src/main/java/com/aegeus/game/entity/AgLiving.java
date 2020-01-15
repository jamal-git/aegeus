package com.aegeus.game.entity;

import com.aegeus.game.Spawner;
import com.aegeus.game.item.ItemManager;
import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.wrapper.ArmorItem;
import com.aegeus.game.util.Util;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AgLiving extends AgEntity {
    private final List<ItemStack> drops = new ArrayList<>();
    private long attackDelay = 900;
    private long lastAttack;
    private Spawner source;

    public AgLiving(LivingEntity entity) {
        super(entity);
    }

    /**
     * Updates the entity's statistics according to their equipment.
     */
    public void update() {
        int health = 0;

        // Retrieve stats from armor
        for (ItemStack i : getEntity().getEquipment().getArmorContents())
            if (ItemManager.id(i).equals(ArmorItem.IDENTITY)) {
                ArmorItem armor = (ArmorItem) ItemManager.get(i);
                health += armor.getHealth();
            }

        if (getEntity() instanceof Player) health += 100;

        getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Math.max(1, health));
        getEntity().setHealth(Math.min(getEntity().getHealth(), Util.getMaxHealth(getEntity())));
    }

    public LivingEntity getEntity() {
        return (LivingEntity) super.getEntity();
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    /**
     * Adds non-null items to the entity's drops.
     *
     * @param items The items to add.
     */
    public void addDrops(ItemStack... items) {
        drops.addAll(Arrays.stream(items)
                .filter(i -> !ItemUtils.isNothing(i))
                .collect(Collectors.toList()));
    }

    public long getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(long attackDelay) {
        this.attackDelay = attackDelay;
    }

    public long getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(long lastAttack) {
        this.lastAttack = lastAttack;
    }

    /**
     * Attempts to resets the attack timer.
     *
     * @return True if reset succeeded.
     */
    public boolean attack() {
        if (Math.abs(System.currentTimeMillis() - lastAttack) >= getAttackDelay()) {
            lastAttack = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public Spawner getSource() {
        return source;
    }

    public void setSource(Spawner source) {
        this.source = source;
    }

    public boolean hasSource() {
        return getSource() != null;
    }

    /**
     * Checks whether the entity is within 25 blocks of their source.
     *
     * @return Whether the entity is near their source.
     */
    public boolean isNearSource() {
        return getSource() != null && getSource().getLocation().distance(getEntity().getLocation()) <= 25;
    }
}
