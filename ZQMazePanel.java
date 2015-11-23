package maze;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jack on 15/11/21.
 */
public class ZQMazePanel extends JPanel {
    private static final int ROOM_SIZE = 40;
    private static final int WALL_THICKNESS = 6;
    private static final int MARGIN = 20;

    public ZQMazePanel(Maze maze) {
        setMaze(maze);
    }

    public Maze getMaze() {
        return maze;
    }

    protected void setMaze(Maze maze){
        this.maze = null;
        this.maze = maze;
        if (maze != null) {
            maze.setView(this);
            Dimension d = maze.getDimension();
            if (d != null) {
                dim = new Dimension(d.width * ROOM_SIZE + 2 * MARGIN,
                        d.height * ROOM_SIZE + 2 * MARGIN);
            }
            addKeyListener(new ZQMazeKeyListener(maze));
        }
        repaint();
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        if (maze != null) {
            maze.draw(g);
        }
        requestFocus();
    }

    protected void mazeUndo(){
        maze.undoCommand();
    }

    //public boolean isFocusTraversable() { // pre 1.4
    public boolean isFocusable() { // 1.4
        return true;
    }

    public Dimension getPreferredSize() {
        return dim;
    }

    public Dimension getMinimumSize() {
        return dim;
    }

    private Maze maze;
    private Dimension dim;
}
