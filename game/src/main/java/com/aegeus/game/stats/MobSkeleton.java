package com.aegeus.game.stats;

import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.stats.impl.Mob;
import com.aegeus.game.util.Chance;
import com.aegeus.game.util.IntPoss;
import com.aegeus.game.util.Util;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class MobSkeleton extends Mob {
	@Override
	public void setup(Object... args) {
		copy(Util.findTier(args));
		getNames().add("Skeleton");
		getTypes().add(EntityType.SKELETON);
		Map<EnumCraftingMaterial, Chance<IntPoss>> drops = (getDrops() == null ? new HashMap<>() : getDrops());
		drops.put(EnumCraftingMaterial.SKELETON_BONE, new Chance<>(new IntPoss(1, 2), 1));
		setDrops(drops);
	}

	@Override
	public String getId() {
		return "skeleton";
	}
}
