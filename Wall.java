

package maze;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Wall implements MapSite {

    public static final Color WALL_COLOR = Color.orange;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void enter(Maze maze) {
        try{
//            FileInputStream doorAu = new FileInputStream("./src/Wall.wav");
            URL auURL = getClass().getResource("res/Wall.wav");
            AudioStream as = new AudioStream(auURL.openStream());
            AudioPlayer.player.start(as);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        hurts.play();
    }

    public void draw(Graphics g, int x, int y, int w, int h) {
        g.setColor(WALL_COLOR);
        g.fillRect(x, y, w, h);
    }

//    protected static AudioClip hurts = util.AudioUtility.getAudioClip("audio/that.hurts.au");

}
