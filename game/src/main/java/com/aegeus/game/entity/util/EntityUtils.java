package com.aegeus.game.entity.util;

import com.aegeus.game.Aegeus;
import org.bukkit.entity.Entity;

public class EntityUtils {
	public static String getName(Entity entity) {
		EntityBox entities = Aegeus.getInstance().getEntities();
		if (entities.contains(entity) && !entities.get(entity).getName().isEmpty())
			return entities.get(entity).getName();
		return entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
	}
}
