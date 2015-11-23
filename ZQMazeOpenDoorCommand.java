package maze;

import java.util.List;

/**
 * ZQMazeOpenDoorCommand is open door command.
 * Created by Zhonghua on 15/11/21.
 */
public class ZQMazeOpenDoorCommand implements UndoableCommand{
    @Override
    public void undo() {
        if (doors != null){
            if (doors.size() != 0)
                for (Door door:doors)
                    maze.setDoor(door, false);
        }
    }

    @Override
    public void execute() {
        doors = maze.getDoorsAround();
        if (doors.size() != 0)
            for (Door door:doors)
            maze.setDoor(door, true);
    }
    protected Maze maze;

    public ZQMazeOpenDoorCommand(Maze maze) {
        this.maze = maze;
    }
    /* Record doors have been opened. */
    List<Door> doors;
}
