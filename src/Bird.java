
import java.awt.Image;
import javax.swing.JLabel;

public class Bird extends JLabel {
    private final Image img;
    private final int width;
    private final int height;
    private final int x;
    private int y;
    private double gravity;
    private double velocity = -10;

    // Image birdImage = new ImageIcon("bird.png").getImage();
    public Bird(Image img, int x, int y, int width, int height) {
        this.img = img;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    public void fall() {
        this.velocity += gravity;
        velocity = Math.min(velocity, 20);
        y += (int) velocity;
        y = Math.min(y, Game.GAME_HEIGHT - height);
    }
    // public void setVelocity(double velocity) {
    //     this.velocity = velocity;
    // }
    public void jump(int jumpForce) {
        this.velocity = jumpForce;
        this.y += (int) velocity;
    }
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    public Image getImage() {
        return img;
    }
    public int getwidth() {
        return width;
    }
    public int getheight() {
        return height;
    }
    public int getx() {
        return x;
    }
    public int gety() {
        return y;
    }
}
