package com.aegeus.game.item.tool;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.EnumCraftingMaterial;
import com.aegeus.game.item.EnumMaterialRarity;
import com.aegeus.game.util.InventoryBuilder;
import com.aegeus.game.util.Util;
import com.google.common.base.Objects;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CraftingCompendium {

	private Map<EnumCraftingMaterial, Integer> items;

	public CraftingCompendium() {
		items = new HashMap<>();
	}

	public static InventoryBuilder getInventoryBuilder(AgPlayer p) {
		InventoryBuilder b = new InventoryBuilder(p.getPlayer(), 9, "Crafting Compendium");
		ItemStack common = new ItemStack(Material.STAINED_GLASS, 1, (byte) 0);
		Util.setDisplayName(common, EnumMaterialRarity.COMMON.getNameAndColor());
		b.setItem(0, common, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.COMMON, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack uncommon = new ItemStack(Material.STAINED_GLASS, 1, (byte) 5);
		Util.setDisplayName(uncommon, EnumMaterialRarity.UNCOMMON.getNameAndColor());
		b.setItem(1, uncommon, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.UNCOMMON, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack rare = new ItemStack(Material.STAINED_GLASS, 1, (byte) 3);
		Util.setDisplayName(rare, EnumMaterialRarity.RARE.getNameAndColor());
		b.setItem(2, rare, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.RARE, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack arcane = new ItemStack(Material.STAINED_GLASS, 1, (byte) 2);
		Util.setDisplayName(arcane, EnumMaterialRarity.ARCANE.getNameAndColor());
		b.setItem(3, arcane, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.ARCANE, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack unique = new ItemStack(Material.STAINED_GLASS, 1, (byte) 1);
		Util.setDisplayName(unique, EnumMaterialRarity.UNIQUE.getNameAndColor());
		b.setItem(4, unique, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.UNIQUE, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack divine = new ItemStack(Material.STAINED_GLASS, 1, (byte) 9);
		Util.setDisplayName(divine, EnumMaterialRarity.DIVINE.getNameAndColor());
		b.setItem(5, divine, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.DIVINE, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack celestial = new ItemStack(Material.STAINED_GLASS, 1, (byte) 14);
		Util.setDisplayName(celestial, EnumMaterialRarity.CELESTIAL.getNameAndColor());
		b.setItem(6, celestial, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.CELESTIAL, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack mythical = new ItemStack(Material.STAINED_GLASS, 1, (byte) 11);
		Util.setDisplayName(mythical, EnumMaterialRarity.MYTHICAL.getNameAndColor());
		b.setItem(7, mythical, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.MYTHICAL, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		ItemStack legendary = new ItemStack(Material.STAINED_GLASS, 1, (byte) 4);
		Util.setDisplayName(legendary, EnumMaterialRarity.LEGENDARY.getNameAndColor());
		b.setItem(8, legendary, (InventoryClickEvent e) -> {
			e.setCancelled(true);
			InventoryBuilder submenu = getSubmenu(EnumMaterialRarity.LEGENDARY, p);
			submenu.setItem(26, Util.getGoBack(), (InventoryClickEvent e1) -> {
				e1.setCancelled(true);
				b.show(p.getPlayer());
			}, true);
			submenu.show(p.getPlayer());
		}, true);
		return b;
	}

	public static InventoryBuilder getInventoryBuilder(Player p) {
		return getInventoryBuilder(Aegeus.getInstance().getPlayer(p));
	}

	private static InventoryBuilder getSubmenu(EnumMaterialRarity e, AgPlayer p) {
		InventoryBuilder menu = new InventoryBuilder(p.getPlayer(), 27, e.getName());
		Iterator<EnumCraftingMaterial> materials = EnumMaterialRarity.getMaterialsByRarity(e).iterator();
		for (int i = 0; i < EnumMaterialRarity.getMaterialsByRarity(e).size(); i++) {
			EnumCraftingMaterial material = materials.next();
			ItemStack stack = material.getItem();
			ItemMeta meta = stack.getItemMeta();
			List<String> lore = meta.getLore();
			lore.add(Util.colorCodes("&7Display Item"));
			meta.setLore(lore);
			meta.setDisplayName(Util.colorCodes(meta.getDisplayName() + ": &f" + +p.getCraftingCompendium().get(material)));
			stack.setItemMeta(meta);
			menu.setItem(i, stack, e1 -> e1.setCancelled(true), false);
		}
		return menu;
	}

	public int get(EnumCraftingMaterial e) {
		if (items.containsKey(e)) {
			return items.get(e);
		}
		items.put(e, 0);
		return 0;
	}

	public int add(EnumCraftingMaterial e, int amount) {
		return items.put(e, get(e) + amount);
	}

	public int subtract(EnumCraftingMaterial e, int amount) {
		return items.put(e, get(e) - amount);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CraftingCompendium that = (CraftingCompendium) o;
		return Objects.equal(items, that.items);
	}

	@Override
	public String toString() {
		return items.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(items);
	}
}