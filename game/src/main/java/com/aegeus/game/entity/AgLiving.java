package com.aegeus.game.entity;

import com.aegeus.game.entity.util.EntityUtils;
import com.aegeus.game.tools.Trap;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AgLiving extends AgEntity {
	private boolean dead = false;
	private List<ItemStack> drops = new ArrayList<>();
	private Trap trap = null;

	public AgLiving(LivingEntity entity) {
		super(entity);
	}

	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}

	public boolean isDead() {
		return dead;
	}

	public void die() {
		if (dead) return;
		dead = true;

		LivingEntity e = (LivingEntity) getEntity().getWorld()
				.spawnEntity(getEntity().getLocation(), EntityType.SKELETON);
		e.getEquipment().setItemInMainHand(getEntity().getEquipment().getItemInMainHand());
		e.getEquipment().setItemInOffHand(getEntity().getEquipment().getItemInOffHand());
		e.getEquipment().setArmorContents(getEntity().getEquipment().getArmorContents());
		e.setInvulnerable(true);
		e.setAI(false);

		getEntity().remove();
		uuid = e.getUniqueId();
	}

	public List<ItemStack> getDrops() {
		return drops;
	}

	public void setDrops(List<ItemStack> drops) {
		this.drops = drops;
	}

	public Trap getTrap() {
		return trap;
	}

	public void setTrap(Trap trap) {
		this.trap = trap;
	}

	public boolean isTrapped() {
		return trap != null;
	}

	public void subtractTrapTime() {
		trap.setTime(trap.getTime() - 1);
		if (isTrapped() && trap.getTime() <= 0)
			trap = null;
	}

	public void trap(Trap trap) {
		this.trap = trap;
		trap.setEntity(this);
		EntityUtils.updateEffects(this);
	}
}
