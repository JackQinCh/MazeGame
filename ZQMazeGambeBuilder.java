package maze;

import java.util.Random;

/**
 * Created by jack on 15/11/21.
 */
public final class ZQMazeGambeBuilder {
    private static int COL = 3;                 // dimension of maze
    private static int ROW = 3;                 // dimension of maze
    private static boolean[][] north;     // is there a wall to north of cell i, j
    private static boolean[][] east;
    private static boolean[][] south;
    private static boolean[][] west;
    private static boolean[][] visited;
    private static boolean done = false;

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    private static int vactory;
    private static int vactoryX;
    private static int vactoryY;

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }


    public static Maze createMaze(MazeBuilder builder, int row, int col) {
        ROW = row;
        COL = col;
        builder.newMaze();

        init();
        generate();

        System.out.println("North:");
        for (int i = 0; i < ROW+2; i++) {
            for (int j = 0; j < COL+2; j++) {
                if (north[j][i])
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println(" ");
        }
        System.out.println("South:");
        for (int i = 0; i < ROW+2; i++) {
            for (int j = 0; j < COL+2; j++) {
                if (south[j][i])
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println(" ");
        }
        System.out.println("East:");
        for (int i = 0; i < ROW+2; i++) {
            for (int j = 0; j < COL+2; j++) {
                if (east[j][i])
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println(" ");
        }
        System.out.println("West:");
        for (int i = 0; i < ROW+2; i++) {
            for (int j = 0; j < COL+2; j++) {
                if (west[j][i])
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println(" ");
        }

        Maze maze = buildRoom(builder);

        vactoryX = random.nextInt(COL);
        if (vactoryX == 0)
            vactoryX = COL;
        vactoryY = random.nextInt(ROW);
        if (vactoryY == 0)
            vactoryY = ROW;
        vactory = (vactoryY-1)*COL + vactoryX;
        if (vactory == 1){
            vactoryX = COL;
            vactoryY = ROW;
            vactory = (vactoryY-1)*COL + vactoryX;
        }
        System.out.println("vactoryX:"+vactoryX+", vactoryY:"+vactoryY+", vactoryRoom:"+vactory);
        maze.setVictoryRoom(vactory);
        return maze;
    }

    private static Maze buildRoom(MazeBuilder builder) {
        for (int i = 0; i < COL*ROW; i++) {
            builder.buildRoom( i+1 );
        }
        for (int i = 1; i <= COL; i++) {
            for (int j = 1; j <= ROW; j++) {
                if (!east[i][j]){
                    int r = random.nextInt(Math.min(COL, ROW));
                    if (r == 0)
                        builder.buildDoor((j-1)*COL + i, (j-1)*COL + i + 1, Direction.EAST, false);
                    else
                        builder.buildDoor((j-1)*COL + i, (j-1)*COL + i + 1, Direction.EAST, true);

                    int x = (j-1)*COL + i;
                    int y = (j-1)*COL + i + 1;
                    System.out.println("West: room1("+x+"), room2("+y+")");
                }
                if (!north[i][j]){
                    int r = random.nextInt(Math.min(COL, ROW));
                    if (r == 0)
                        builder.buildDoor((j-1)*COL + i, j*COL + i, Direction.NORTH, false);
                    else
                        builder.buildDoor((j-1)*COL + i, j*COL + i, Direction.NORTH, true);

                    int x = (j-1)*COL + i;
                    int y = j*COL + i;
                    System.out.println("North: room1("+x+"), room2("+y+")");
                }
            }
        }

        return builder.getMaze();
    }

    private static void init() {
        // initialize border cells as already visited
        visited = new boolean[COL+2][ROW+2];
        for (int x = 0; x < COL+2; x++) {
            visited[x][0] = true;
            visited[x][ROW+1] = true;
        }
        for (int y = 0; y < ROW+2; y++) {
            visited[0][y] = true;
            visited[COL+1][y] = true;
        }


        // initialze all walls as present
        north = new boolean[COL+2][ROW+2];
        east  = new boolean[COL+2][ROW+2];
        south = new boolean[COL+2][ROW+2];
        west  = new boolean[COL+2][ROW+2];
        for (int x = 0; x < COL+2; x++) {
            for (int y = 0; y < ROW+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }


    /**
     * Generate the maze
     * @param x
     * @param y
     */
    private static void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            // pick random neighbor (could use Knuth's trick instead)
            while (true) {
                int r = random.nextInt(4);
                if (r == 0 && !visited[x][y+1]) {//North
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r == 1 && !visited[x+1][y]) {//East
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r == 2 && !visited[x][y-1]) {//South
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (r == 3 && !visited[x-1][y]) {//West
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    /**
     * Generate the maze starting from lower left
     */
    private static void generate() {
        generate(1, 1);
    }


    /**
     * Solve the maze using depth-first search
     * @param x
     * @param y
     * @param mazePanel
     * @param direction
     */
    private static void solve(int x, int y, ZQMazePanel mazePanel, Direction direction) {
        if (x == 0 || y == 0 || x == COL+1 || y == ROW+1) return;
        if (done || visited[x][y]) return;
        visited[x][y] = true;

        if (direction != null) {
            MapSite site = mazePanel.getMaze().getCurrentRoom().getSide(direction);
            if (site instanceof Door) {
                if (!((Door) site).isOpen()) {
                    mazePanel.getMaze().setDoor(((Door) site), true);
                    mazePanel.paintImmediately(0, 0, mazePanel.getWidth(), mazePanel.getHeight());
                }
            }
            Command command = new MazeMoveCommand(mazePanel.getMaze(), direction);
            mazePanel.getMaze().doCommand(command);
            mazePanel.paintImmediately(0, 0, mazePanel.getWidth(), mazePanel.getHeight());
        }

        // Reached goal
        if (x == vactoryX && y == vactoryY){
            done = true;
        }

        try{
            Thread.currentThread().sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Solving: "+x+", "+y);
        System.out.println("Current Room: "+ mazePanel.getMaze().getCurrentRoom().getRoomNumber());

        if (!north[x][y]){
            solve(x, y+1, mazePanel, Direction.NORTH);
        }
        if (!east[x][y]){
            solve(x+1, y, mazePanel, Direction.EAST);
        }
        if (!south[x][y]){
            solve(x, y-1, mazePanel, Direction.SOUTH);
        }
        if (!west[x][y]){
            solve(x-1, y, mazePanel, Direction.WEST);
        }

        if (done){
            System.out.println("Solve Done!");
            return;
        }
        mazePanel.getMaze().undoCommand();
        mazePanel.paintImmediately(0, 0, mazePanel.getWidth(), mazePanel.getHeight());

        try{
            Thread.currentThread().sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // solve the maze starting from the start state
    public static void solve(ZQMazePanel mazePanel) {
        System.out.println("Start Solve");
        int currentRoomNum = mazePanel.getMaze().getCurrentRoom().getRoomNumber();
        System.out.println("Current Room:"+currentRoomNum);

        int X = currentRoomNum % COL;
        int Y = currentRoomNum / COL + 1;
        if (X == 0){
            X = COL;
            Y = Y - 1;
        }
        System.out.println("X:"+X+", Y:"+Y);

        for (int x = 1; x <= COL; x++)
            for (int y = 1; y <= ROW; y++)
                visited[x][y] = false;
        done = false;

        solve(X, Y, mazePanel, null);

    }


}
