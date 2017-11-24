package com.aegeus.game.stats;

import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.IntPoss;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class StatsSkeleton extends Stats {
	public StatsSkeleton() {

	}

	public StatsSkeleton(Stats parent) {
		super(parent);
	}

	@Override
	public void prepare() {
		getNames().add("Skeleton");
		getTypes().add(EntityType.SKELETON);
		Map<EnumCraftingMaterial, Chance<IntPoss>> drops = (getDrops() == null ? new HashMap<>() : getDrops());
		drops.put(EnumCraftingMaterial.SKELETON_BONE, new Chance<>(new IntPoss(1, 2), 1));
		setDrops(drops);
	}
}
