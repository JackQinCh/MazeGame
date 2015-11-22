package maze;

import java.util.Random;

/**
 * Created by jack on 15/11/21.
 */
public final class ZQMazeGambeBuilder {
    private static int N = 5;                 // dimension of maze
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


    public static Maze createMaze(MazeBuilder builder) {
        builder.newMaze();

        init();
        generate();

        return buildRoom(builder);
    }

    private static Maze buildRoom(MazeBuilder builder) {
        for (int i = 0; i < N*N; i++) {
            builder.buildRoom( i+1 );
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (!east[j][i]){
                    int r = random.nextInt(N);
                    if (r == 0)
                        builder.buildDoor((i-1)*N + j, (i-1)*N + j + 1, Direction.EAST, false);
                    else
                        builder.buildDoor((i-1)*N + j, (i-1)*N + j + 1, Direction.EAST, true);
                }
                if (!north[j][i]){
                    int r = random.nextInt(N);
                    if (r == 0)
                        builder.buildDoor((i-1)*N + j, (i-1)*N + j + N, Direction.NORTH, false);
                    else
                        builder.buildDoor((i-1)*N + j, (i-1)*N + j + N, Direction.NORTH, true);
                }
            }
        }

//        builder.buildDoor(1, 2, Direction.EAST, true);
//        builder.buildDoor(2, 3, Direction.EAST, true);
//        builder.buildDoor(3, 6, Direction.NORTH, true);
//        builder.buildDoor(5, 6, Direction.EAST, true);
//        builder.buildDoor(4, 5, Direction.EAST, false);
//        builder.buildDoor(6, 9, Direction.NORTH, true);
//        builder.buildDoor(7, 8, Direction.EAST, true);
//        builder.buildDoor(8, 9, Direction.EAST, true);


        return builder.getMaze();
    }

    private static void init() {
        // initialize border cells as already visited
        visited = new boolean[N+2][N+2];
        for (int x = 0; x < N+2; x++) {
            visited[x][0] = true;
            visited[x][N+1] = true;
        }
        for (int y = 0; y < N+2; y++) {
            visited[0][y] = true;
            visited[N+1][y] = true;
        }


        // initialze all walls as present
        north = new boolean[N+2][N+2];
        east  = new boolean[N+2][N+2];
        south = new boolean[N+2][N+2];
        west  = new boolean[N+2][N+2];
        for (int x = 0; x < N+2; x++) {
            for (int y = 0; y < N+2; y++) {
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
        if (x == 0 || y == 0 || x == N+1 || y == N+1) return;
        if (done || visited[x][y]) return;
        visited[x][y] = true;

//        StdDraw.setPenColor(StdDraw.BLUE);
//        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
//        StdDraw.show(30);

        // reached middle
        if (x == N/2 && y == N/2) done = true;

        if (!north[x][y]) solve(x, y + 1);
        if (!east[x][y])  solve(x + 1, y);
        if (!south[x][y]) solve(x, y - 1);
        if (!west[x][y])  solve(x - 1, y);

        if (done) return;

//        StdDraw.setPenColor(StdDraw.GRAY);
//        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
//        StdDraw.show(30);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int x = 1; x <= N; x++)
            for (int y = 1; y <= N; y++)
                visited[x][y] = false;
        done = false;
        solve(1, 1);
    }

}
