package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jack on 15/11/21.
 */
public class ZQMain extends JFrame{

    Maze maze;
    MazeBuilder builder;
    ZQMazePanel mazePanel;

    public ZQMain(String name) {
        maze = ZQThemesFactory.createMazeWithTheme("Default");

        mazePanel = new ZQMazePanel(maze);

        JMenuBar menubar = makeMenuBar(mazePanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(menubar, BorderLayout.NORTH);
        getContentPane().add(mazePanel, BorderLayout.CENTER);
        pack();
        Dimension frameDim = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - frameDim.width / 2,
                screenSize.height / 2 - frameDim.height / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    protected JMenuBar makeMenuBar(ZQMazePanel mazePanel){
        JMenuBar menubar = new JMenuBar();

        JMenu menu = new JMenu("Command");
        JMenuItem menuItem = new JMenuItem("undo");
        menuItem.addActionListener(new MazeCommandAction(mazePanel));
        menu.add(menuItem);
        menubar.add(menu);

        menu = new JMenu("Themes");
        String[] themes = ZQThemesFactory.getThemes();
        for (String theme:themes){
            menuItem = new JMenuItem(theme);
            menuItem.setActionCommand(theme);
            menuItem.addActionListener(new ThemeCommandAction(mazePanel));
            menu.add(menuItem);
        }
        menubar.add(menu);


        return menubar;
    }

    static class MazeCommandAction implements ActionListener {

        public MazeCommandAction(ZQMazePanel mazePanel) {
            this.mazePanel = mazePanel;
        }

        public void actionPerformed(ActionEvent event) {
            mazePanel.mazeUndo();
        }

        protected ZQMazePanel mazePanel;
    }

    static class ThemeCommandAction implements ActionListener {

        public ThemeCommandAction(ZQMazePanel mazePanel) {
            this.mazePanel = mazePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String theme = e.getActionCommand();
            mazePanel.setMaze(ZQThemesFactory.createMazeWithTheme(theme));
        }
        protected ZQMazePanel mazePanel;
    }

    public static void main(String[] args) {

        JFrame frame;
        frame = new ZQMain("Maze -- Zhonghua");
        frame.setVisible(true);

    }
}
