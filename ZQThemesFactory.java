package maze;


/**
 * Created by jack on 15/11/21.
 */
public final class ZQThemesFactory {
    private static MazeFactory factory = new MazeFactory();
    private static int ROW = 8;
    private static int COL = 7;

    private ZQThemesFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static final String[] themes = {"Harry", "Snow", "Default"};

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
