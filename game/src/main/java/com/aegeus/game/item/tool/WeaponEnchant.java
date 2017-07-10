package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.item.Tier;
import com.aegeus.game.item.info.SingletonInfo;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;

public class WeaponEnchant extends AgItem implements SingletonInfo {
	private LocalDateTime time = LocalDateTime.now();
	private int tier = 0;

	public WeaponEnchant(int tier) {
		super(Material.EMPTY_MAP);
		Tier t = Tier.fromTier(tier);
		setName("&f&lEnchant:&7 " + t.getColor() + t.getWeapon() + " Weapon");
		addLore("&c+5% DMG");
		addLore("&7&oWeapon will VANISH if enchant above +3 FAILS.");
		this.tier = tier;
	}

	public WeaponEnchant(ItemStack item) {
		super(item);
		impo();
	}

	@Override
	public void impo() {
		SingletonInfo.impo(this);

		NBTTagCompound info = getAegeusInfo();
		tier = info.hasKey("tier") ? info.getInt("tier") : -1;
	}

	@Override
	public void store() {
		SingletonInfo.store(this);

		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("enchant_weapon"));
		info.set("tier", new NBTTagInt(tier));
		setAegeusInfo(info);
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("enchant_weapon");
	}

	@Override
	public ItemStack build() {
		store();
		return super.build();
	}

	@Override
	public LocalDateTime getTime() {
		return time;
	}

	@Override
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public int getTier() {
		return tier;
	}

}