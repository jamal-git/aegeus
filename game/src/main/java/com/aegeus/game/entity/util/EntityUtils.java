package com.aegeus.game.entity.util;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgMonster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityUtils {
	public static String getName(Entity entity) {
		Aegeus aegeus = Aegeus.getInstance();

		if (aegeus.getEntities().contains(entity) && aegeus.get(entity) instanceof AgMonster) {
			AgMonster info = aegeus.getMonster((LivingEntity) entity);
			if (!info.getName().isEmpty()) return info.getName();
		}

		return entity.getCustomName() == null ? entity.getName() : entity.getCustomName();
	}
}
