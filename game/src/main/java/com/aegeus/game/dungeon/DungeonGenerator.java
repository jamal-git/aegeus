package com.aegeus.game.dungeon;

import com.aegeus.game.Aegeus;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Silvre on 7/15/2017.
 */
public class DungeonGenerator {
    private transient ThreadLocalRandom random = ThreadLocalRandom.current();
    private transient Aegeus parent = Aegeus.getInstance();
    private int arraySize;
    private int numberOfSegments;
    private int startExitDistance;
    private String[][] result;
    public DungeonGenerator()   {
        arraySize = 7;
        numberOfSegments = 10;
        startExitDistance = 2;
    }

    public String[][] generateMaze()    {
        String[][] maze = new String[arraySize][arraySize];
        do {
            for (int i = 0; i < maze.length; i++) {
                for (int i1 = 0; i1 < maze[i].length; i1++) {
                    maze[i][i1] = "0";
                }
            }
            int sx = 0, sy = 0, ex = 0, ey = 0;
            boolean solution = false;
            for (int i = 0; i < 100; i++) {
                if(Point2D.distance(sx = random.nextInt(arraySize), sy = random.nextInt(arraySize), ex = random.nextInt(arraySize), ey = random.nextInt(arraySize)) >= startExitDistance && sx != ex && sy != ey)    {
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
        return result;
    }

    private boolean dfsrecursive(int x, int y, String[][] maze)   {
        if(x < 0 || y < 0 || x > (arraySize - 1) || y > (arraySize - 1))    return false;
        if(maze[x][y].equalsIgnoreCase("E")) return true;
        if(maze[x][y].equalsIgnoreCase("P")) return false;
        maze[x][y] = "P";
        if((x < (arraySize - 1) && maze[x + 1][y].equalsIgnoreCase("E")) || (x > 0 && maze[x - 1][y].equalsIgnoreCase("E"))
                || (y < (arraySize - 1) && maze[x][y + 1].equalsIgnoreCase("E")) || (y > 0 && maze[x][y - 1].equalsIgnoreCase("E"))) {
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

//        private void bfs()    {
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

    private boolean validateAndMap(String[][] maze)    {
        if(!isValid(maze)) return false;
        String[][] map = new String[arraySize][arraySize];
        int keysToPlace = (int) Math.ceil(numberOfSegments / 5.0);
        while(keysToPlace != 0) {
            int x,y;
            boolean success = false;
            for (int i = 0; i < 100; i++) {
                if(maze[x = random.nextInt(arraySize)][y = random.nextInt(arraySize)].equalsIgnoreCase("0") && nearby(x, y, maze) == 1 && notNearbyStartOrExitOrKey(x, y, maze))  {
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
                        map[i][j] = "SQ"; //4-WAY QUAD JUNCTION
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
        result = map;
        return true;
    }

    private boolean isValid(String[][] maze)    {
        int count = 0;
        for(String[] arr : maze)
            for(String s : arr)
                if(s.equalsIgnoreCase("P")) count++;
        if(count != numberOfSegments) return false;
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                if(nearby(i,j, maze) > 2) return false;
        return true;
    }

    private int getDirectionalCount(int x, int y, String[][] maze)   {
        int count = 0;
        if(x < (arraySize - 1) && maze[x + 1][y].matches("[PpKkSsEe]")) count += 1; //SOUTH
        if(x > 0 && maze[x - 1][y].matches("[PpKkSsEe]")) count += 3; //NORTH
        if(y > 0 && maze[x][y - 1].matches("[PpKkSsEe]")) count += 2; //WEST
        if(y < (arraySize - 1) && maze[x][y + 1].matches("[PpKkSsEe]")) count += 6; //EAST
        return count;
    }

    private int nearby(int x, int y, String[][] maze)  {
        int count = 0;
        if(x > 0 && maze[x - 1][y].matches("[PpKkSsEe]")) count++;
        if(x < (arraySize - 1) && maze[x + 1][y].matches("[PpKkSsEe]")) count++;
        if(y > 0 && maze[x][y - 1].matches("[PpKkSsEe]")) count++;
        if(y < (arraySize - 1) && maze[x][y + 1].matches("[PpKkSsEe]")) count++;
        return count;
    }

    public boolean notNearbyStartOrExitOrKey(int x, int y, String[][] maze)  {
        return !(getDirection(x, y, maze, Direction.SOUTH).matches("[SsEeKk]") || getDirection(x, y, maze, Direction.EAST).matches("[SsEeKk]") ||
                getDirection(x, y, maze, Direction.WEST).matches("[SsEeKk]") || getDirection(x, y, maze, Direction.NORTH).matches("[SsEeKk]"));
    }

    private String getDirection(int x, int y, String[][] maze, Direction d)   {
        if(d == Direction.NORTH && x > 0) return maze[x - 1][y];
        if(d == Direction.SOUTH && x < (arraySize - 1)) return maze[x + 1][y];
        if(d == Direction.EAST && y < (arraySize - 1)) return maze[x][y + 1];
        if(d == Direction.WEST && y > 0) return maze[x][y - 1];
        return "";
    }

    public DungeonGenerator withStartExitDistance(int distance) {
        startExitDistance = distance;
        return this;
    }

    public DungeonGenerator withArraySize(int size) {
        arraySize = size;
        return this;
    }

    public DungeonGenerator withNumberOfSegments(int segments)  {
        numberOfSegments = segments;
        return this;
    }

    enum Direction  {
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
}