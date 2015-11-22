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



        return buildRoom(builder);
    }

    private static Maze buildRoom(MazeBuilder builder) {
        for (int i = 0; i < COL*ROW; i++) {
            builder.buildRoom( i+1 );
        }
        for (int i = 1; i <= ROW; i++) {
            for (int j = 1; j <= COL; j++) {
                if (!east[j][i]){
                    int r = random.nextInt(Math.min(COL, ROW));
                    if (r == 0)
                        builder.buildDoor((i-1)*COL + j, (i-1)*COL + j + 1, Direction.EAST, false);
                    else
                        builder.buildDoor((i-1)*COL + j, (i-1)*COL + j + 1, Direction.EAST, true);

                    int x = (i-1)*COL + j;
                    int y = (i-1)*COL + j + 1;
                    System.out.println("West: room1("+x+"), room2("+y+")");
                }
                if (!north[j][i]){
                    int r = random.nextInt(Math.min(COL, ROW));
                    if (r == 0)
                        builder.buildDoor((i-1)*COL + j, i*COL + j, Direction.NORTH, false);
                    else
                        builder.buildDoor((i-1)*COL + j, i*COL + j, Direction.NORTH, true);

                    int x = (i-1)*COL + j;
                    int y = i*COL + j;
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


    // generate the maze
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

    // generate the maze starting from lower left
    private static void generate() {
        generate(1, 1);
    }



    // solve the maze using depth-first search
    private void solve(int x, int y) {
        if (x == 0 || y == 0 || x == COL+1 || y == ROW+1) return;
        if (done || visited[x][y]) return;
        visited[x][y] = true;

//        StdDraw.setPenROWor(StdDraw.BLUE);
//        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
//        StdDraw.show(30);

        // reached middle
        if (x == COL/2 && y == ROW/2) done = true;

        if (!north[x][y]) solve(x, y + 1);
        if (!east[x][y])  solve(x + 1, y);
        if (!south[x][y]) solve(x, y - 1);
        if (!west[x][y])  solve(x - 1, y);

        if (done) return;

//        StdDraw.setPenROWor(StdDraw.GRAY);
//        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
//        StdDraw.show(30);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int x = 1; x <= COL; x++)
            for (int y = 1; y <= ROW; y++)
                visited[x][y] = false;
        done = false;
        solve(1, 1);
    }

}
