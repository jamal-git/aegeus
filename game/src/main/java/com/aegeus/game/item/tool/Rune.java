package com.aegeus.game.item.tool;

import com.aegeus.game.item.AgItem;
import com.aegeus.game.util.Util;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagInt;
import net.minecraft.server.v1_9_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Rune extends AgItem {
	private RuneType runeType = null;

	public Rune(RuneType runeType) {
		super(Material.NETHER_STAR);
		this.runeType = runeType;
	}

	public Rune(ItemStack item) {
		super(item);
		impo();
	}

	public Rune(Rune other) {
		super(other);
		this.runeType = other.runeType;
	}

	@Override
	public void impo() {
		NBTTagCompound info = getAegeusInfo();
		runeType = (info.hasKey("runeType")) ? RuneType.fromId(info.getInt("runeType")) : null;
	}

	@Override
	public void store() {
		super.store();

		NBTTagCompound info = getAegeusInfo();
		info.set("type", new NBTTagString("rune"));
		info.set("runeType", new NBTTagInt(runeType.getId()));
		setAegeusInfo(info);
	}

	@Override
	public boolean verify() {
		NBTTagCompound info = getAegeusInfo();
		return info.hasKey("type") && info.getString("type").equalsIgnoreCase("rune");
	}

	@Override
	public ItemStack build() {
		store();
		ItemStack item = super.build();

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Util.colorCodes("&f&lRune:&d " + runeType.getName()));

		List<String> lore = new ArrayList<>();
		Arrays.stream(runeType.getDesc()).forEach(s -> lore.add(Util.colorCodes("&7" + s)));
		lore.add(Util.colorCodes("&fSlots: &7" + runeType.getSlots().stream().map(RuneType.Slot::name).collect(Collectors.joining(", "))));
		meta.setLore(lore);

		item.setItemMeta(meta);

		return item;
	}

	public RuneType getRuneType() {
		return runeType;
	}

	public void setRuneType(RuneType runeType) {
		this.runeType = runeType;
	}

	public enum RuneType {
		ARCANE_MIGHT(0, "Arcane Might", new String[]{
				"Increase magic damage by 10%."},
				Arrays.asList(Slot.ARMOR)),
		BLOOD_HUNT(1, "Blood Hunt", new String[]{
				"Increase physical damage by 10%",
				"against targets below 20% health."},
				Arrays.asList(Slot.WEAPON));

		private final int id;
		private final String name;
		private final String[] desc;
		private final List<Slot> slots;

		RuneType(int id, String name, String[] desc, List<Slot> slots) {
			this.id = id;
			this.name = name;
			this.desc = desc;
			this.slots = slots;
		}

		public static RuneType fromId(int id) {
			for (RuneType t : values())
				if (t.id == id) return t;
			return null;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String[] getDesc() {
			return desc;
		}

		public List<Slot> getSlots() {
			return slots;
		}

		@Override
		public String toString() {
			return getName();
		}

		public enum Slot {
			ARMOR,
			WEAPON
		}
	}
}
