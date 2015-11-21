package maze;


/**
 * Created by jack on 15/11/21.
 */
public final class ZQThemesFactory {
    private ZQThemesFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static final String[] themes = {"Harry", "Snow", "Default"};

    public static MazeFactory createFactoryWithTheme(String theme){
        switch (theme){
            case "Harry":
                return new HarryPotterMazeFactory();
            case "Snow":
                return new SnowWhiteMazeFactory();
            case "Default":
                return new MazeFactory();
            default:
                return new MazeFactory();
        }
    }

    public static String[] getThemes() {
        return themes;
    }
}
