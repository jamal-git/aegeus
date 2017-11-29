package com.aegeus.game.dungeon;

import com.aegeus.game.Aegeus;
import com.aegeus.game.dungeon.DungeonGenerator.Direction;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.item.Items;
import com.aegeus.game.stats.impl.Tier;
import com.aegeus.game.item.tool.Enchant;
import com.aegeus.game.social.Party;
import com.aegeus.game.stats.impl.Mob;
import com.aegeus.game.util.Util;
import com.aegeus.game.util.exceptions.DungeonLoadingException;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.apache.commons.io.IOUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Dungeon {
	private static transient Aegeus parent = Aegeus.getInstance();
	String[][] layout;
	private transient WorldEditPlugin worldedit = Aegeus.getWorldEdit();
	private transient EditSession editSession;
	private transient int segmentSize = 5;
	private transient Location origin;
	private Party party = null;
	private int keysLeft = 2;

	private World world;
	private File directory;

	private List<CuboidClipboard> starts = new ArrayList<>();
	private List<CuboidClipboard> straights = new ArrayList<>();
	private List<CuboidClipboard> turns = new ArrayList<>();
	private List<CuboidClipboard> trijunctions = new ArrayList<>();
	private List<CuboidClipboard> quadjunctions = new ArrayList<>();
	private List<CuboidClipboard> keys = new ArrayList<>();
	private List<CuboidClipboard> exits = new ArrayList<>();

	public Dungeon(Party p, Location l, String directory, int startExitDistance, World w, int arraySize, int numberOfSegments, int segmentSize) throws IOException, DataException {
		setOrigin(l);
		System.out.println(p);
		keysLeft = (int) Math.ceil(numberOfSegments / 5.0);
		party = p;
		setWorld(w);
		this.setSegmentSize(segmentSize);
		editSession = new EditSession(new BukkitWorld(getWorld()), worldedit.getLocalConfiguration().maxChangeLimit);
		File temp = new File(parent.getDataFolder() + "/dungeons/zips/" + directory + ".zip");
		if (!temp.exists() || temp.isDirectory()) {
			throw new DungeonLoadingException("The dungeon selected does not exist or has been corrupted.");
		} else if (!new File(parent.getDataFolder() + "/dungeons/" + directory).exists()) {
			parent.getLogger().info("Unzipping dungeon...");
			this.setDirectory(temp);
			//noinspection ResultOfMethodCallIgnored
			new File(parent.getDataFolder() + "/dungeons/" + directory + "/").mkdir();
			try (ZipFile zipfile = new ZipFile(temp)) {
				Enumeration<? extends ZipEntry> entries = zipfile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					File entryDestination = new File(Aegeus.getInstance().getDataFolder() + "/dungeons/" + directory + "/", entry.getName());
					if (entry.isDirectory())
						entryDestination.mkdirs();
					else {
						entryDestination.getParentFile().mkdirs();
						InputStream in = zipfile.getInputStream(entry);
						OutputStream out = new FileOutputStream(entryDestination);
						IOUtils.copy(in, out);
						IOUtils.closeQuietly(in);
						out.close();
					}
				}
			} catch (IOException e) {
				parent.getLogger().log(Level.SEVERE, "Could not load dungeon", e);
				return;
			}
			parent.getLogger().info("Finished unzipping, initializing dungeon...");
		} else {
			parent.getLogger().info("Unzipped dungeon already, initializing dungeon...");
		}
		//noinspection ConstantConditions
		for (File folder : new File(Aegeus.getInstance().getDataFolder() + "/dungeons/" + directory + "/").listFiles()) {
			//noinspection ConstantConditions
			for (File f : folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".schematic")))
				switch (f.getParentFile().getName()) {
					case "starts":
						starts.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded a start variation");
						break;
					case "exits":
						exits.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded an exit variation");
						break;
					case "keys":
						keys.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded a key variation");
						break;
					case "straights":
						straights.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded a straight variation");
						break;
					case "turns":
						turns.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded a turn variation");
						break;
					case "tris":
						trijunctions.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded a tri junction variation");
						break;
					case "quads":
						quadjunctions.add(SchematicFormat.MCEDIT.load(f));
						parent.getLogger().info("Loaded a quad junction variation");
						break;
					default:
						parent.getLogger().log(Level.SEVERE, "FAILED TO LOAD DUNGEON", new DungeonLoadingException("Invalid Zip file, check for any random or missing files!"));
						return;
				}
		}
		parent.getLogger().info("Finished importing schematics, generating dungeon layout...");
		layout = new DungeonGenerator().withArraySize(arraySize).withNumberOfSegments(numberOfSegments).withStartExitDistance(startExitDistance).generateMaze();
		printArray(layout);
		parent.getServer().getScheduler().runTask(parent, () -> {
			try {
				build(getOrigin());
			} catch (MaxChangedBlocksException e) {
				e.printStackTrace();
			}
		});
	}

	public void build(Location l) throws MaxChangedBlocksException {
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout.length; j++) {
				world.loadChunk(l.getBlockX() + 16 * i, l.getBlockZ() + 16 * j, true);
			}
		}
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[i].length; j++) {
				if (!layout[i][j].equalsIgnoreCase("00")) {
					CuboidClipboard clipboard;
					Direction direction = null;
					char character = layout[i][j].charAt(0);
					for (Direction d : Direction.values())
						if (character == d.getChar()) {
							direction = d;
							break;
						}
					if (direction == null) {
						throw new DungeonLoadingException("Shit mapping code, go look at this shit and fix it lol");
					}
					switch (layout[i][j].charAt(1)) {
						case 'S':
							clipboard = starts.get(Util.rInt(starts.size()));
							break;
						case 'K':
							clipboard = keys.get(Util.rInt(keys.size()));
							break;
						case 'E':
							clipboard = exits.get(Util.rInt(keys.size()));
							break;
						case 'I':
							clipboard = straights.get(Util.rInt(straights.size()));
							break;
						case 'T':
							clipboard = turns.get(Util.rInt(turns.size()));
							break;
						case 'J':
							clipboard = trijunctions.get(Util.rInt(trijunctions.size()));
							break;
						case 'Q':
							clipboard = quadjunctions.get(Util.rInt(quadjunctions.size()));
							break;
						default:
							throw new DungeonLoadingException("Shit mapping code, go look at this shit and fix it lol");
					}
					Vector spot = new Vector(l.getBlockX() + getSegmentSize() * j, l.getBlockY(), l.getBlockZ() + getSegmentSize() * i);
					clipboard.rotate2D(direction.getRotateValue());
					clipboard.paste(editSession, spot, false);
					clipboard.rotate2D(360 - direction.getRotateValue());
					if (layout[i][j].charAt(1) == 'S') {
						for (int x = spot.getBlockX() - getSegmentSize() / 2; x < spot.getX() + getSegmentSize() / 2; x++)
							for (int y = spot.getBlockY() - getSegmentSize() / 2; y < spot.getY() + getSegmentSize() / 2; y++)
								for (int z = spot.getBlockZ() - getSegmentSize() / 2; z < spot.getZ() + getSegmentSize() / 2; z++) {
									Block b = getWorld().getBlockAt(x, y, z);
									Block b2 = getWorld().getBlockAt(x, y - 1, z);
									Block b3 = getWorld().getBlockAt(x, y - 2, z);
									if (b != null && b.getType() == Material.EMERALD_BLOCK &&
											b2 != null && b2.getType() == Material.GLOWSTONE &&
											b3 != null && b3.getType() == Material.EMERALD_BLOCK)
										world.setSpawnLocation(x, y + 1, z);
								}
					} else if (layout[i][j].charAt(1) == 'K') {
						for (int x = spot.getBlockX() - getSegmentSize() / 2; x < spot.getX() + getSegmentSize() / 2; x++)
							for (int y = spot.getBlockY() - getSegmentSize() / 2; y < spot.getY() + getSegmentSize() / 2; y++)
								for (int z = spot.getBlockZ() - getSegmentSize() / 2; z < spot.getZ() + getSegmentSize() / 2; z++) {
									Block b = getWorld().getBlockAt(x, y, z);
									if (b != null && b.getType() == Material.CHEST) {
										if (Util.rFloat() <= 0.4) {
											int item = Util.rInt(5);
											if (item == 0)
												((Chest) b.getState()).getInventory().addItem(Tier.get(3).getHelmet().get(
														Util.rarity(Util.rFloat())).build());
											else if (item == 1)
												((Chest) b.getState()).getInventory().addItem(Tier.get(3).getChestplate().get(
														Util.rarity(Util.rFloat())).build());
											else if (item == 2)
												((Chest) b.getState()).getInventory().addItem(Tier.get(3).getLeggings().get(
														Util.rarity(Util.rFloat())).build());
											else if (item == 3)
												((Chest) b.getState()).getInventory().addItem(Tier.get(3).getBoots().get(
														Util.rarity(Util.rFloat())).build());
											else if (item == 4)
												((Chest) b.getState()).getInventory().addItem(Tier.get(3).getWeapon().get(
														Util.rarity(Util.rFloat())).build());
										}

										if (Util.rFloat() <= 0.2) {
											((Chest) b.getState()).getInventory().addItem(new Enchant(3, Util.rInt(2)).build());
										}

										if (Util.rFloat() <= 0.7) {
											((Chest) b.getState()).getInventory().addItem(Items.getGold(Util.rInt(1, 120)));
										}
									}
								}
					}
					for (int x = spot.getBlockX() - getSegmentSize() / 2; x < spot.getX() + getSegmentSize() / 2; x++)
						for (int y = spot.getBlockY() - getSegmentSize() / 2; y < spot.getY() + getSegmentSize() / 2; y++)
							for (int z = spot.getBlockZ() - getSegmentSize() / 2; z < spot.getZ() + getSegmentSize() / 2; z++) {
								Block b = l.getWorld().getBlockAt(x, y, z);
								if (b != null && b.getType() == Material.PUMPKIN) {
									Mob.get("skeleton", 3).spawn(new Location(world, x, y, z));
									b.setType(Material.AIR);
								}
							}
				}
			}
		}
		party.getLeader().getPlayer().sendMessage(Util.colorCodes("&7Dungeon has finished loading, teleporting in..."));
		for (AgPlayer p : party.getMembers()) {
			p.getPlayer().teleport(getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}
	}

	public void useKey() {
		if (--keysLeft == 0) {
			party.getMembers().stream().map(AgPlayer::getPlayer).forEach(x -> {
				//noinspection deprecation
				x.sendTitle(Util.colorCodes("&dDUNGEON COMPLETE!"), "");
				x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			});
		} else if (keysLeft == 1)
			party.sendMessage(Util.colorCodes("&d1 key remains."), null, true);
		else
			party.sendMessage(Util.colorCodes("&d" + keysLeft + " keys remain."), null, true);
		party.getMembers().stream().map(AgPlayer::getPlayer).forEach(x -> x.playSound(x.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1, 1));
	}

	private void printArray(String[][] array) {
		for (String[] a : array) {
			parent.getLogger().info(String.join(" ", a));
		}
	}

	public int getSegmentSize() {
		return segmentSize;
	}

	private void setSegmentSize(int segmentSize) {
		this.segmentSize = segmentSize;
	}

	public Location getOrigin() {
		return origin;
	}

	private void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Party getParty() {
		return party;
	}

	private void setParty(Party party) {
		this.party = party;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public File getDirectory() {
		return directory;
	}

	private void setDirectory(File directory) {
		this.directory = directory;
	}


	private class Node {
		private int x, y;
		private Node parent;

		public Node(int x, int y, Node parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}

		public Node getParent() {
			return parent;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Node && ((Node) obj).getX() == x && ((Node) obj).getY() == y;
		}
	}
}