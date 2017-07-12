package com.aegeus.game.dungeon;

import com.aegeus.game.Aegeus;
import com.aegeus.game.util.exceptions.DungeonLoadingException;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.apache.commons.io.IOUtils;

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
    String[][] layout;
    private static transient Aegeus parent = Aegeus.getInstance();
    private transient ThreadLocalRandom random = ThreadLocalRandom.current();
    private transient WorldEditPlugin worldedit = Aegeus.getWorldEdit();
    private File directory;
    private List<CuboidClipboard> starts = new ArrayList<>();
    private List<CuboidClipboard> straights = new ArrayList<>();
    private List<CuboidClipboard> turns = new ArrayList<>();
    private List<CuboidClipboard> trijunctions = new ArrayList<>();
    private List<CuboidClipboard> quadjunctions = new ArrayList<>();
    private List<CuboidClipboard> keys = new ArrayList<>();
    private List<CuboidClipboard> exits = new ArrayList<>();
    private transient int length = 5;

    public Dungeon(String directory, int length) throws DungeonLoadingException, IOException, DataException {
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

    public void dfs()    {
        String[][] maze = {
                {"0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0"}};
        do {
            for (int i = 0; i < maze.length; i++) {
                for (int i1 = 0; i1 < maze[i].length; i1++) {
                    maze[i][i1] = "0";
                }
            }
            int sx, sy, ex, ey;
            //noinspection ControlFlowStatementWithoutBraces
            while (Point2D.distance(sx = random.nextInt(7), sy = random.nextInt(7), ex = random.nextInt(7), ey = random.nextInt(7)) < length || sx == ex || sy == ey)
                ;
            maze[sx][sy] = "S";
            maze[ex][ey] = "E";
            dfsrecursive(sx, sy, maze);
            maze[sx][sy] = "S";
        } while(!validateAndMap(maze));
        parent.getLogger().info("SUCCESSFULLY CREATED DUNGEON!");
    }

    private boolean dfsrecursive(int x, int y, String[][] maze)   {
        if(x < 0 || y < 0 || x > 6 || y > 6)    return false;
        if(maze[x][y].equalsIgnoreCase("E")) return true;
        if(maze[x][y].equalsIgnoreCase("P")) return false;
        maze[x][y] = "P";
        if((x < 6 && maze[x + 1][y].equalsIgnoreCase("E")) || (x > 0 && maze[x - 1][y].equalsIgnoreCase("E"))
                || (y < 6 && maze[x][y + 1].equalsIgnoreCase("E")) || (y > 0 && maze[x][y - 1].equalsIgnoreCase("E"))) {
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
        if(count != 10) return false;
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                if(nearby(i,j, maze) > 2) return false;
        return true;
    }

    private boolean validateAndMap(String[][] maze)    {
        if(!isValid(maze)) return false;
        String[][] map = new String[7][7];
        int keysToPlace = 2;
        while(keysToPlace != 0) {
            int x,y;
            //noinspection ControlFlowStatementWithoutBraces
            boolean success = false;
            for (int i = 0; i < 100; i++) {
                if(maze[x = random.nextInt(7)][y = random.nextInt(7)].equalsIgnoreCase("0") && nearby(x, y, maze) == 1 && notNearbyStartOrExitOrKey(x, y, maze))  {
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
                            map[i][j] = "VS"; //UP DOWN STRAIGHT
                        else if(count == 8)
                            map[i][j] = "HS"; //LEFT RIGHT STRAIGHT
                        else if(count == 7)
                            map[i][j] = "DT"; //SOUTH EAST TURN
                        else if(count == 9)
                            map[i][j] = "RT"; //NORTH EAST TURN
                        else if(count == 5)
                            map[i][j] = "UT"; //NORTH WEST TURN
                        else if(count == 3)
                            map[i][j] = "LT"; //SOUTH WEST TURN
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
        if(d == Direction.SOUTH && x < 6) return maze[x + 1][y];
        if(d == Direction.EAST && y < 6) return maze[x][y + 1];
        if(d == Direction.WEST && y > 0) return maze[x][y - 1];
        return "";
    }


    private int getDirectionalCount(int x, int y, String[][] maze)   {
        int count = 0;
        if(x < 6 && maze[x + 1][y].matches("[PpKkSsEe]")) count += 1; //SOUTH
        if(x > 0 && maze[x - 1][y].matches("[PpKkSsEe]")) count += 3; //NORTH
        if(y > 0 && maze[x][y - 1].matches("[PpKkSsEe]")) count += 2; //WEST
        if(y < 6 && maze[x][y + 1].matches("[PpKkSsEe]")) count += 6; //EAST
        return count;
    }

    private int nearby(int x, int y, String[][] maze)  {
        int count = 0;
        if(x > 0 && maze[x - 1][y].matches("[PpKkSsEe]")) count++;
        if(x < 6 && maze[x + 1][y].matches("[PpKkSsEe]")) count++;
        if(y > 0 && maze[x][y - 1].matches("[PpKkSsEe]")) count++;
        if(y < 6 && maze[x][y + 1].matches("[PpKkSsEe]")) count++;
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

    private class Node   {
        private int x,y;
        private Node parent;
        public Node(int x, int y, Node parent)   {
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

    private enum Direction  {
        NORTH(1),
        SOUTH(1),
        WEST(2),
        EAST(2);

        private int direction;
        Direction(int value)  {
            this.direction = value;
        }

        public int getDirection() {
            return direction;
        }
    }
}