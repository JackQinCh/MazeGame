package maze;

import java.awt.*;

public interface MapSite extends Cloneable {

    Object clone() throws CloneNotSupportedException;
    void enter(Maze maze);
    void draw(Graphics g, int x, int y, int w, int h);

}
