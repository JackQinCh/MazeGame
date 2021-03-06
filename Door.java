package maze;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Door implements MapSite {

    public Door(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setRooms(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Room otherSideFrom(Room room) {
        if (room != null) {
            if (room == room1) {
                return room2;
            } else if (room == room2) {
                return room1;
            }
        }
        return null;
    }

    public void enter(Maze maze) {
        if (open) {
            Room otherRoom = otherSideFrom(maze.getCurrentRoom());
            if (otherRoom != null) {
                otherRoom.enter(maze);
            }
        } else {
            try{
//                FileInputStream doorAu = new FileInputStream("./src/Door.wav");
                URL auURL = getClass().getResource("res/Door.wav");
                AudioStream as = new AudioStream(auURL.openStream());
                AudioPlayer.player.start(as);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics g, int x, int y, int w, int h) {
        g.setColor(Wall.WALL_COLOR);
        g.fillRect(x, y, w, h);
        if (orientation == Orientation.VERTICAL) {
            y += 2 * w; h -= 4 * w;
        } else {
            x += 2 * h; w -= 4 * h;
        }
        if (open) {
            g.setColor(Room.ROOM_COLOR);
            g.fillRect(x, y, w, h);
        } else {
            g.setColor(Color.red);
            g.fillRect(x, y, w, h);
            g.setColor(Color.black);
            g.drawRect(x, y, w, h);
        }
    }

    protected Room room1;
    protected Room room2;
    protected boolean open;
    protected Orientation orientation;

}
