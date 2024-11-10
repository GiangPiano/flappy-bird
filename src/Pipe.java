
import java.awt.Image;
import javax.swing.JPanel;

public class Pipe extends JPanel{
    private final int pipeWidth;
    private final int pipeHeight;
    private final Image img;
    private int pipex;
    private int pipey;
    private int speed;
    private boolean passed;

    public Pipe(Image img, int pipex, int pipey, int pipeWidth, int pipeHeight) {
        this.img = img;
        this.pipex = pipex;
        this.pipey = pipey;
        this.pipeWidth = pipeWidth;
        this.pipeHeight = pipeHeight;
        this.passed = false;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void move() {
        this.pipex -= speed;
    }
    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    public Image getImage() {
        return img;
    }
    public int getx() {
        return pipex;
    }
    public int gety() {
        return pipey;
    }
    public int getwidth() {
        return pipeWidth;
    }
    public int getheight() {
        return pipeHeight;
    }
}
