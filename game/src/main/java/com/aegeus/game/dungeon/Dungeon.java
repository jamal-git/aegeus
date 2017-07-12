package com.aegeus.game.dungeon;

import com.aegeus.game.Aegeus;
import com.aegeus.game.stats.StatsSkeleton;
import com.aegeus.game.stats.StatsT3;
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
import org.bukkit.World;
import org.bukkit.block.Block;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int size = 7;
    private transient int distance = 5;
    private transient int segments = 10;

    private World world;
    private File directory;

    private List<CuboidClipboard> starts = new ArrayList<>();
    private List<CuboidClipboard> straights = new ArrayList<>();
    private List<CuboidClipboard> turns = new ArrayList<>();
    private List<CuboidClipboard> trijunctions = new ArrayList<>();
    private List<CuboidClipboard> quadjunctions = new ArrayList<>();
    private List<CuboidClipboard> keys = new ArrayList<>();
    private List<CuboidClipboard> exits = new ArrayList<>();

    public Dungeon(String directory, int distance, World w, int size, int segments) throws DungeonLoadingException, IOException, DataException {
        this.distance = distance;
        world = w;
        this.size = size;
        this.segments = segments;
        editSession = new EditSession(new BukkitWorld(world), worldedit.getLocalConfiguration().maxChangeLimit);
        File temp = new File(parent.getDataFolder() + "/dungeons/zips/" + directory + ".zip");
        if(!temp.exists() || temp.isDirectory())   {
            throw new DungeonLoadingException("The dungeon selected does not exist or has been corrupted.");
        }
        else if(!new File(parent.getDataFolder() + "/dungeons/" + directory).exists())    {
            parent.getLogger().info("Unzipping dungeon...");
            this.directory = temp;
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
        dfs();
        printArray(layout);
    }

    public void build(Location l) throws DungeonLoadingException, MaxChangedBlocksException {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if(!layout[i][j].equalsIgnoreCase("00")) {
                    CuboidClipboard clipboard;
                    Direction direction = null;
                    char character = layout[i][j].charAt(0);
                    for(Direction d : Direction.values())   {
                        if(character == d.getChar())    {
                            direction = d;
                            break;
                        }
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
                    Vector spot = new Vector(l.getBlockX() + 5 * j, l.getBlockY(), l.getBlockZ() + 5 * i);
                    clipboard.rotate2D(direction.getRotateValue());
                    clipboard.paste(editSession, spot, false);
                    clipboard.rotate2D(360 - direction.getRotateValue());
					for (int x = spot.getBlockX() - 2; x < spot.getX() + 2; x++)
						for (int y = spot.getBlockY() - 2; y < spot.getY() + 2; y++)
							for (int z = spot.getBlockZ() - 2; z < spot.getZ() + 2; z++) {
								Block b = l.getWorld().getBlockAt(x, y, z);
								if (b != null && b.getType().equals(Material.PUMPKIN)) {
									new StatsSkeleton(new StatsT3()).spawn(new Location(l.getWorld(), x, y, z));
									b.setType(Material.AIR);
								}
							}
				}
            }
        }

    }

    public void dfs()    {
        String[][] maze = new String[size][size];
        do {
            for (int i = 0; i < maze.length; i++) {
                for (int i1 = 0; i1 < maze[i].length; i1++) {
                    maze[i][i1] = "0";
                }
            }
            int sx = 0, sy = 0, ex = 0, ey = 0;
            boolean solution = false;
            for (int i = 0; i < 100; i++) {
                if(Point2D.distance(sx = random.nextInt(size), sy = random.nextInt(size), ex = random.nextInt(size), ey = random.nextInt(size)) >= distance && sx != ex && sy != ey)    {
                    solution = true;
                    break;
                }
            }
            if(solution) {
                maze[sx][sy] = "S";
                maze[ex][ey] = "E";
                dfsrecursive(sx, sy, maze);
                maze[sx][sy] = "S";
            }
        } while(!validateAndMap(maze));
        parent.getLogger().info("SUCCESSFULLY CREATED DUNGEON!");
    }

    private boolean dfsrecursive(int x, int y, String[][] maze)   {
        if(x < 0 || y < 0 || x > (size - 1) || y > (size - 1))    return false;
        if(maze[x][y].equalsIgnoreCase("E")) return true;
        if(maze[x][y].equalsIgnoreCase("P")) return false;
        maze[x][y] = "P";
        if((x < (size - 1) && maze[x + 1][y].equalsIgnoreCase("E")) || (x > 0 && maze[x - 1][y].equalsIgnoreCase("E"))
                || (y < (size - 1) && maze[x][y + 1].equalsIgnoreCase("E")) || (y > 0 && maze[x][y - 1].equalsIgnoreCase("E"))) {
            return true;
        }
        if(nearby(x, y, maze) > 1) {
            maze[x][y] = "0";
            return false;
        }
        boolean pathFound = false;
        switch(random.nextInt(4))    {
            case 0:
                pathFound = dfsrecursive(x + 1, y, maze) || dfsrecursive(x - 1, y, maze) || dfsrecursive(x, y + 1, maze) || dfsrecursive(x, y - 1, maze);
                break;
            case 1:
                pathFound = dfsrecursive(x - 1, y, maze) || dfsrecursive(x, y + 1, maze) || dfsrecursive(x, y - 1, maze) || dfsrecursive(x + 1, y, maze);
                break;
            case 2:
                pathFound = dfsrecursive(x, y + 1, maze) || dfsrecursive(x, y - 1, maze) || dfsrecursive(x + 1, y, maze) || dfsrecursive(x - 1, y, maze);
                break;
            case 3:
                pathFound = dfsrecursive(x, y - 1, maze) || dfsrecursive(x + 1, y, maze) || dfsrecursive(x - 1, y, maze) || dfsrecursive(x, y + 1, maze);
                break;
            default:
                break;
        }
        if(pathFound)    {
            maze[x][y] = "P";
            return true;
        }
        maze[x][y] = "0";
        return false;
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

    private boolean isValid(String[][] maze)    {
        int count = 0;
        for(String[] arr : maze)
            for(String s : arr)
                if(s.equalsIgnoreCase("P")) count++;
        if(count != segments) return false;
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                if(nearby(i,j, maze) > 2) return false;
        return true;
    }

    private boolean validateAndMap(String[][] maze)    {
        if(!isValid(maze)) return false;
        String[][] map = new String[size][size];
        int keysToPlace = 2;
        while(keysToPlace != 0) {
            int x,y;
            //noinspection ControlFlowStatementWithoutBraces
            boolean success = false;
            for (int i = 0; i < 100; i++) {
                if(maze[x = random.nextInt(size)][y = random.nextInt(size)].equalsIgnoreCase("0") && nearby(x, y, maze) == 1 && notNearbyStartOrExitOrKey(x, y, maze))  {
                    success = true;
                    maze[x][y] = "K";
                    break;
                }
            }
            if(!success) return false;
            keysToPlace--;
        }
        for (int i = 0; i < maze.length; i++)
            map[i] = Arrays.copyOf(maze[i], maze[i].length);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if(maze[i][j].equalsIgnoreCase("P"))    {
                    int surround = nearby(i, j, maze);
                    int count = getDirectionalCount(i, j, maze);
                    if(surround == 2) {
                        if(count == 4)
                            map[i][j] = "SI"; //UP DOWN STRAIGHT
                        else if(count == 8)
                            map[i][j] = "EI"; //LEFT RIGHT STRAIGHT
                        else if(count == 7)
                            map[i][j] = "ST"; //SOUTH EAST TURN
                        else if(count == 9)
                            map[i][j] = "ET"; //NORTH EAST TURN
                        else if(count == 5)
                            map[i][j] = "NT"; //NORTH WEST TURN
                        else if(count == 3)
                            map[i][j] = "WT"; //SOUTH WEST TURN
                    }
                    if(surround == 3)   {
                        if(count == 9)
                            map[i][j] = "SJ"; //WEST SOUTH EAST JUNCTION
                        if(count == 10)
                            map[i][j] = "EJ"; //SOUTH EAST NORTH JUNCTION
                        if(count == 11)
                            map[i][j] = "NJ"; //EAST NORTH WEST JUNCTION
                        if(count == 6)
                            map[i][j] = "WJ"; //NORTH WEST SOUTH JUNCTION
                    }
                    if(surround == 4)   {
                        map[i][j] = "QQ"; //4-WAY QUAD JUNCTION
                    }
                }
                else if(maze[i][j].matches("[KkSsEe]")) {
                    String s = maze[i][j];
                    switch(getDirectionalCount(i, j, maze)) {
                        case 1:
                            if(s.matches("[Kk]")) //SOUTH
                                map[i][j] = "SK";
                            else if(s.matches("[Ss]"))
                                map[i][j] = "SS";
                            else map[i][j] = "SE";
                            break;
                        case 3:
                            if(s.matches("[Kk]")) //NORTH
                                map[i][j] = "NK";
                            else if(s.matches("[Ss]"))
                                map[i][j] = "NS";
                            else map[i][j] = "NE";
                            break;
                        case 2:
                            if(s.matches("[Kk]")) //WEST
                                map[i][j] = "WK";
                            else if(s.matches("[Ss]"))
                                map[i][j] = "WS";
                            else map[i][j] = "WE";
                            break;
                        case 6:
                            if(s.matches("[Kk]")) //EAST
                                map[i][j] = "EK";
                            else if(s.matches("[Ss]"))
                                map[i][j] = "ES";
                            else map[i][j] = "EE";
                            break;
                        default:
                            break;
                    }
                }
                else map[i][j] = "00";
            }
        }
        layout = map;
        return true;
    }

    private String getDirection(int x, int y, String[][] maze, Direction d)   {
        if(d == Direction.NORTH && x > 0) return maze[x - 1][y];
        if(d == Direction.SOUTH && x < (size - 1)) return maze[x + 1][y];
        if(d == Direction.EAST && y < (size - 1)) return maze[x][y + 1];
        if(d == Direction.WEST && y > 0) return maze[x][y - 1];
        return "";
    }


    private int getDirectionalCount(int x, int y, String[][] maze)   {
        int count = 0;
        if(x < (size - 1) && maze[x + 1][y].matches("[PpKkSsEe]")) count += 1; //SOUTH
        if(x > 0 && maze[x - 1][y].matches("[PpKkSsEe]")) count += 3; //NORTH
        if(y > 0 && maze[x][y - 1].matches("[PpKkSsEe]")) count += 2; //WEST
        if(y < (size - 1) && maze[x][y + 1].matches("[PpKkSsEe]")) count += 6; //EAST
        return count;
    }

    private int nearby(int x, int y, String[][] maze)  {
        int count = 0;
        if(x > 0 && maze[x - 1][y].matches("[PpKkSsEe]")) count++;
        if(x < (size - 1) && maze[x + 1][y].matches("[PpKkSsEe]")) count++;
        if(y > 0 && maze[x][y - 1].matches("[PpKkSsEe]")) count++;
        if(y < (size - 1) && maze[x][y + 1].matches("[PpKkSsEe]")) count++;
        return count;
    }

    public boolean notNearbyStartOrExitOrKey(int x, int y, String[][] maze)  {
        return !(getDirection(x, y, maze, Direction.SOUTH).matches("[SsEeKk]") || getDirection(x, y, maze, Direction.EAST).matches("[SsEeKk]") ||
                getDirection(x, y, maze, Direction.WEST).matches("[SsEeKk]") || getDirection(x, y, maze, Direction.NORTH).matches("[SsEeKk]"));
    }

    private void printArray(String[][] array)   {
        for(String[] a : array)  {
            parent.getLogger().info(String.join(" ", a));
        }
    }

    private enum Direction  {
        NORTH(1, 'N', 180),
        SOUTH(1, 'S', 0),
        WEST(2, 'W', 90),
        EAST(2, 'E', 270);

        private int direction;
        private char c;
        private int rotateValue;
        Direction(int value, char c, int rotateValue)  {
            this.direction = value;
            this.c = c;
            this.rotateValue = rotateValue;
        }

        public int getDirection() {
            return direction;
        }

        public char getChar()   {
            return c;
        }

        public int getRotateValue() {
            return rotateValue;
        }
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