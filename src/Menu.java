import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Menu extends JPanel{
    private final int width = Game.GAME_WIDTH;
    private final int height = Game.GAME_HEIGHT;
    Image backgroundImage;
    public Menu() {
        setPreferredSize(new Dimension(width, height));
        // backgroundImage = new ImageIcon("../assets/menu2.jpg").getImage();
        backgroundImage = new ImageIcon(getClass().getResource("assets/menu2.jpg")).getImage();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
    }
}
