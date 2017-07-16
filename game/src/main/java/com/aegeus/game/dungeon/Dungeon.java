package com.aegeus.game.dungeon;

import com.aegeus.game.Aegeus;
import com.aegeus.game.entity.AgPlayer;
import com.aegeus.game.social.Party;
import com.aegeus.game.stats.StatsSkeleton;
import com.aegeus.game.stats.StatsT3;
import com.aegeus.game.util.Util;
import com.aegeus.game.util.exceptions.DungeonLoadingException;
import com.aegeus.game.dungeon.DungeonGenerator.Direction;
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Silvre on 7/10/2017.
 */
public class Dungeon {
    private static transient Aegeus parent = Aegeus.getInstance();
	private transient ThreadLocalRandom random = ThreadLocalRandom.current();

    private transient WorldEditPlugin worldedit = Aegeus.getWorldEdit();
    private transient EditSession editSession;

    String[][] layout;
    private transient int segmentSize = 5;
    private transient Location origin;
    private Party party = null;

    private World world;
    private File directory;

    private List<CuboidClipboard> starts = new ArrayList<>();
    private List<CuboidClipboard> straights = new ArrayList<>();
    private List<CuboidClipboard> turns = new ArrayList<>();
    private List<CuboidClipboard> trijunctions = new ArrayList<>();
    private List<CuboidClipboard> quadjunctions = new ArrayList<>();
    private List<CuboidClipboard> keys = new ArrayList<>();
    private List<CuboidClipboard> exits = new ArrayList<>();

    public Dungeon(Party p, Location l, String directory, int startExitDistance, World w, int arraySize, int numberOfSegments, int segmentSize) throws DungeonLoadingException, IOException, DataException {
        setOrigin(l);
        System.out.println(p);
        party = p;
        setWorld(w);
        this.setSegmentSize(segmentSize);
        editSession = new EditSession(new BukkitWorld(getWorld()), worldedit.getLocalConfiguration().maxChangeLimit);
        File temp = new File(parent.getDataFolder() + "/dungeons/zips/" + directory + ".zip");
        if(!temp.exists() || temp.isDirectory())   {
            throw new DungeonLoadingException("The dungeon selected does not exist or has been corrupted.");
        }
        else if(!new File(parent.getDataFolder() + "/dungeons/" + directory).exists())    {
            parent.getLogger().info("Unzipping dungeon...");
            this.setDirectory(temp);
            //noinspection ResultOfMethodCallIgnored
            new File(parent.getDataFolder() + "/dungeons/" + directory + "/").mkdir();
            try(ZipFile zipfile = new ZipFile(temp))    {
                Enumeration<? extends ZipEntry> entries = zipfile.entries();
                while (entries.hasMoreElements())   {
                    ZipEntry entry = entries.nextElement();
                    File entryDestination = new File(Aegeus.getInstance().getDataFolder() + "/dungeons/" + directory + "/", entry.getName());
                    if (entry.isDirectory())
                        entryDestination.mkdirs();
                    else    {
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
        }
        else    {
            parent.getLogger().info("Unzipped dungeon already, initializing dungeon...");
        }
        //noinspection ConstantConditions
        for(File folder : new File(Aegeus.getInstance().getDataFolder() + "/dungeons/" + directory + "/").listFiles())   {
            //noinspection ConstantConditions
            for(File f : folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".schematic")))
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
            } catch (DungeonLoadingException | MaxChangedBlocksException e) {
                e.printStackTrace();
            }
        });
    }

    public void build(Location l) throws DungeonLoadingException, MaxChangedBlocksException {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout.length; j++) {
                world.loadChunk(l.getBlockX() + 16 * i, l.getBlockZ() + 16 * j, true);
            }
        }
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++)  {
                if(!layout[i][j].equalsIgnoreCase("00"))    {
                    CuboidClipboard clipboard;
                    Direction direction = null;
                    char character = layout[i][j].charAt(0);
                    for(Direction d : Direction.values())
                        if(character == d.getChar())    {
                            direction = d;
                            break;
                        }
                    if(direction == null)   {
                        throw new DungeonLoadingException("Shit mapping code, go look at this shit and fix it lol");
                    }
                    switch (layout[i][j].charAt(1)) {
                        case 'S':
                            clipboard = starts.get(random.nextInt(starts.size()));
                            break;
                        case 'K':
                            clipboard = keys.get(random.nextInt(keys.size()));
                            break;
                        case 'E':
                            clipboard = exits.get(random.nextInt(keys.size()));
                            break;
                        case 'I':
                            clipboard = straights.get(random.nextInt(straights.size()));
                            break;
                        case 'T':
                            clipboard = turns.get(random.nextInt(turns.size()));
                            break;
                        case 'J':
                            clipboard = trijunctions.get(random.nextInt(trijunctions.size()));
                            break;
                        case 'Q':
                            clipboard = quadjunctions.get(random.nextInt(quadjunctions.size()));
							break;
						default:
                            throw new DungeonLoadingException("Shit mapping code, go look at this shit and fix it lol");
                    }
                    Vector spot = new Vector(l.getBlockX() + getSegmentSize() * j, l.getBlockY(), l.getBlockZ() + getSegmentSize() * i);
                    clipboard.rotate2D(direction.getRotateValue());
                    clipboard.paste(editSession, spot, false);
                    clipboard.rotate2D(360 - direction.getRotateValue());
                    if(layout[i][j].charAt(1) == 'S')    {
                        for (int x = spot.getBlockX() - getSegmentSize() / 2; x < spot.getX() + getSegmentSize() / 2; x++)
                            for (int y = spot.getBlockY() - getSegmentSize() / 2; y < spot.getY() + getSegmentSize() / 2; y++)
                                for (int z = spot.getBlockZ() - getSegmentSize() /2; z < spot.getZ() + getSegmentSize() / 2; z++) {
                                    Block b = getWorld().getBlockAt(x, y, z);
                                    Block b2 = getWorld().getBlockAt(x, y - 1, z);
                                    Block b3 = getWorld().getBlockAt(x, y - 2, z);
                                    if(b != null && b.getType() == Material.EMERALD_BLOCK &&
                                            b2 != null && b2.getType() == Material.GLOWSTONE &&
                                            b3 != null && b3.getType() == Material.EMERALD_BLOCK)
                                        world.setSpawnLocation(x, y + 1, z);
                                }
                    }
					for (int x = spot.getBlockX() - getSegmentSize() / 2; x < spot.getX() + getSegmentSize() / 2; x++)
						for (int y = spot.getBlockY() - getSegmentSize() / 2; y < spot.getY() + getSegmentSize() / 2; y++)
							for (int z = spot.getBlockZ() - getSegmentSize() /2; z < spot.getZ() + getSegmentSize() / 2; z++) {
								Block b = l.getWorld().getBlockAt(x, y, z);
								if (b != null && b.getType() == Material.PUMPKIN) {
									new StatsSkeleton(new StatsT3()).spawn(new Location(world, x, y, z));
									b.setType(Material.AIR);
								}
							}
				}
            }
        }
        party.getLeader().getPlayer().sendMessage(Util.colorCodes("&7Dungeon has finished loading, teleporting in..."));
        for(AgPlayer p : party.getMembers())    {
            p.getPlayer().teleport(getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

//    private void bfs()    {
//        String[][] maze ={
//                {"0","0","0","0","0"},
//                {"0","0","0","0","0"},
//                {"0","0","0","0","0"},
//                {"0","0","0","0","0"},
//                {"0","0","0","0","0"}};
//        int sx, sy, ex, ey;
//        while(Point2D.distance(sx = random.nextInt(7), sy = random.nextInt(7), ex = random.nextInt(7), ey = random.nextInt(7)) < length || sx == ex || sy == ey);
//        maze[sx][sy] = "S";
//        maze[ex][ey] = "E";
//        List<Node> nodes = new ArrayList<>();
//        LinkedList<Node> queue = new LinkedList<>();
//        queue.add(new Node(sx, sy, null));
//        nodes.add(queue.peek());
//        while(!queue.isEmpty()) {
//            System.out.println(queue.size());
//            Node n = queue.poll();
//            if(maze[n.getX()][n.getY()].equalsIgnoreCase("E"))  {
//                List<Node> path = new ArrayList<>();
//                Node step = n.getParent();
//                while(step != null) {
//                    path.add(step);
//                    if(!maze[step.getX()][step.getY()].equalsIgnoreCase("S"))
//                        maze[step.getX()][step.getY()] = "P";
//                    step = step.getParent();
//                }
//                printArray(maze);
//                return;
//            }
//            int x = n.getX(), y = n.getY();
//            Node child;
//            child = new Node(x + 1, y, n);
//            if(x < 6 && !nodes.contains(child)) {
//                nodes.add(child);
//                queue.offer(child);
//            }
//            child = new Node(x - 1, y, n);
//            if(x > 0 && !nodes.contains(child)) {
//                nodes.add(child);
//                queue.offer(child);
//            }
//            child = new Node(x, y - 1, n);
//            if(y > 0 && !nodes.contains(child)) {
//                nodes.add(child);
//                queue.offer(child);
//            }
//            child = new Node(x, y + 1, n);
//            if(y < 6 && !nodes.contains(child)) {
//                nodes.add(child);
//                queue.offer(child);
//            }
//        }
//        printArray(maze);
//        return;
//    }

    private void printArray(String[][] array)   {
        for(String[] a : array)  {
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