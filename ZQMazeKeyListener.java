package maze;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by jack on 15/11/21.
 */
public class ZQMazeKeyListener extends KeyAdapter {

    ZQMazeKeyListener(ZQMazePanel mazePanel) {
        this.mazePanel = mazePanel;
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        Command command = null;
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP:
                System.out.println("Up key");
                command = new MazeMoveCommand(mazePanel.getMaze(), Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Down key");
                command = new MazeMoveCommand(mazePanel.getMaze(), Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Left key");
                command = new MazeMoveCommand(mazePanel.getMaze(), Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Right key");
                command = new MazeMoveCommand(mazePanel.getMaze(), Direction.EAST);
                break;
            case KeyEvent.VK_ENTER:
                command = new ZQMazeOpenDoorCommand(mazePanel.getMaze());
                break;
            default:
                System.out.println("Key press ignored");
        }
        if (command != null) {
            mazePanel.getMaze().doCommand(command);
        }
    }

    ZQMazePanel mazePanel;
}
