import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
public class Runner implements KeyListener {
    static JFrame frame = new JFrame("Flappy Bird");
    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new Runner());

        Menu menu = new Menu();
        frame.add(menu);
        frame.pack();
        frame.setVisible(true);
        
    }
    public static void start() {
        Game game = new Game();
        frame.add(game);
        frame.pack();
        game.requestFocus();
        frame.repaint();
    }
    public static void gameOver() {
        frame.remove(frame.getContentPane());
        frame.repaint();
        Menu menu = new Menu();
        frame.add(menu);
        frame.pack();
        frame.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Space pressed");
            start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}