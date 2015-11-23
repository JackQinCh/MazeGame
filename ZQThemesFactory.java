package maze;


/**
 * Maze static Factory generates maze with given theme.
 * Created by Zhonghua on 15/11/21.
 */
public final class ZQThemesFactory {
    private static MazeFactory factory = new MazeFactory();
    private static int ROW = 5;
    private static int COL = 4;

    private ZQThemesFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static final String[] themes = {"Harry", "Snow", "Default"};

    /**
     * Static Factory
     * Create maze with given theme.
     * @param theme
     * @return Maze
     */
    public static Maze createMazeWithTheme(String theme){
        switch (theme){
            case "Harry":
                factory = new HarryPotterMazeFactory();
                break;
            case "Snow":
                factory = new SnowWhiteMazeFactory();
                break;
            case "Default":
                factory = new MazeFactory();
                break;
            default:
        }

        MazeBuilder builder = new FactoryMazeBuilder(factory);
        Maze maze = ZQMazeGambeBuilder.createMaze(builder, ROW, COL);
        maze.setCurrentRoom(1);
        return maze;
    }

    public static int getROW() {
        return ROW;
    }

    public static int getCOL() {
        return COL;
    }

    /**
     *  Static Factory
     * Create maze with given row and col.
     * @param row
     * @param col
     * @return Maze
     */
    public static Maze createMazeWithSize(int row, int col){
        ROW = row;
        COL = col;
        MazeBuilder builder = new FactoryMazeBuilder(factory);
        Maze maze = ZQMazeGambeBuilder.createMaze(builder, ROW, COL);
        maze.setCurrentRoom(1);
        return maze;
    }

    public static String[] getThemes() {
        return themes;
    }
}
