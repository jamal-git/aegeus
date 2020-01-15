package com.aegeus.game.item.drop;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgLiving;
import com.aegeus.game.item.ItemUtils;
import com.aegeus.game.item.Items;
import com.aegeus.game.util.IntRange;
import com.aegeus.game.util.Rarity;
import com.aegeus.game.util.Util;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.bukkit.inventory.EquipmentSlot.*;

public class DropSet {
    private IntRange goldAmount = new IntRange();
    private float dropChance = 1f;
    private Map<EquipmentSlot, List<Drop>> dropMap = new HashMap<>();

    public List<Drop> get(EquipmentSlot key) {
        if (!dropMap.containsKey(key))
            dropMap.put(key, new ArrayList<>());
        return dropMap.get(key);
    }

    public void set(EquipmentSlot key, List<Drop> set) {
        dropMap.put(key, set);
    }

    public ItemStack generate(EquipmentSlot key) {
        return generate(key, Rarity.get(Util.RANDOM.nextFloat()));
    }

    public ItemStack generate(EquipmentSlot key, float rarity) {
        List<Drop> set = get(key);
        return !set.isEmpty() ? set.get(Util.RANDOM.nextInt(set.size())).get(rarity) : null;
    }

    public void equip(LivingEntity entity) {
        equip(entity, 0);
    }

    public void equip(LivingEntity entity, int minPieces) {
        AgLiving data = Aegeus.getInstance().getEntities().getOrCreateLiving(entity);

        // Generate armor
        ItemStack[] armor = Stream.of(FEET, LEGS, CHEST, HEAD)
                .map(type -> generate(type, Rarity.get(Util.RANDOM.nextFloat())))
                .toArray(ItemStack[]::new);
        // Randomize which armor pieces to remove
        int pieces = Util.nextInt(0, minPieces);
        if (pieces < armor.length) {
            // Create index list without helmet
            List<Integer> indices = IntStream.range(0, armor.length - 1).boxed().collect(Collectors.toList());
            // Shuffle indices
            Collections.shuffle(indices);
            // Add helmet as last possible index
            indices.add(armor.length - 1);
            // Remove randomized indices from armor
            indices.stream().limit(pieces).forEach(i -> armor[i] = null);
        }
        // Equip armor
        entity.getEquipment().setArmorContents(armor);

        // Generate weapon
        entity.getEquipment().setItemInMainHand(generate(HAND, Rarity.get(Util.RANDOM.nextFloat())));
        // Randomize equipment drop
        if (Util.RANDOM.nextFloat() <= dropChance)
            if (Util.RANDOM.nextFloat() <= 0.46f)
                data.addDrops(entity.getEquipment().getItemInMainHand());
            else {
                List<ItemStack> armorDrops = Arrays.stream(entity.getEquipment().getArmorContents())
                        .filter(item -> !ItemUtils.isNothing(item))
                        .collect(Collectors.toList());
                data.addDrops(armorDrops.get(Util.RANDOM.nextInt(armorDrops.size())));
            }
        // Randomize gold drop
        if (Util.RANDOM.nextFloat() <= 0.22f)
            data.addDrops(Items.GOLD.get(goldAmount.get()));

        data.update();
        Util.heal(entity);
    }

    public IntRange getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(IntRange goldAmount) {
        this.goldAmount = goldAmount;
    }

    public float getDropChance() {
        return dropChance;
    }

    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }

    public Map<EquipmentSlot, List<Drop>> getDropMap() {
        return dropMap;
    }
}
