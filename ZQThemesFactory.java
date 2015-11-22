package maze;


/**
 * Created by jack on 15/11/21.
 */
public final class ZQThemesFactory {
    private ZQThemesFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static final String[] themes = {"Harry", "Snow", "Default"};

    public static Maze createMazeWithTheme(String theme){
        MazeFactory factory;

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
                factory = new MazeFactory();
                break;
        }

        MazeBuilder builder = new FactoryMazeBuilder(factory);
        Maze maze = ZQMazeGambeBuilder.createMaze(builder);//Can extend
        maze.setCurrentRoom(1);
        return maze;
    }

    public static String[] getThemes() {
        return themes;
    }
}
