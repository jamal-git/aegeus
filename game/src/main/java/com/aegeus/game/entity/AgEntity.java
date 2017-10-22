package com.aegeus.game.entity;

import com.google.common.base.Objects;
import org.bukkit.entity.Entity;

public class AgEntity {
	private final Entity entity;

	public AgEntity(Entity entity) {
		this.entity = entity;
	}

	public AgEntity(AgEntity other) {
		this.entity = other.entity;
	}

	public Entity getEntity() {
		return entity;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgEntity agEntity = (AgEntity) o;
        return Objects.equal(entity, agEntity.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entity);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("entity", entity)
                .toString();
    }
}
