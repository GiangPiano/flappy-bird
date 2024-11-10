
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {
    // BACKGROUND
    private final Image backgroundImage = new ImageIcon("background1.jpg").getImage();
    public static final int GAME_WIDTH = 360;
    public static final int GAME_HEIGHT = 640;
    // FPS
    private final int FPS = 90;
    Timer gameLoop;
    // BIRD
    private final Image birdImage = new ImageIcon("bird.png").getImage();
    private final Bird bird;
    // PIPE
    private final Image pipeTopImage = new ImageIcon("spiketop.png").getImage();
    // private final Image pipeBottomImage = new ImageIcon("spikebottom.png").getImage();
    Timer pipeLoop;

    // PHYSICS
    private final int JUMP_FORCE = -10;
    private final double GRAVITY = 0.5;
    private final int PIPE_SPEED = 5;
    private ArrayList<Pipe> pipes;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); 
        initializeKeyListener();
        
        bird = new Bird(birdImage, GAME_WIDTH / 8, GAME_HEIGHT / 2, 80, 80);
        bird.setGravity(GRAVITY);

        pipes = new ArrayList<Pipe>();
        pipeLoop = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipe();
            }
        });
        pipeLoop.start();

        gameLoop = new Timer(1000/FPS, this);
        gameLoop.start();
    }
    
    private void initializeKeyListener() {
        addKeyListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void placePipe() {
        Pipe pipeTop = new Pipe(pipeTopImage, GAME_WIDTH, 0, 64, 512);
        pipeTop.setSpeed(PIPE_SPEED);
        pipes.add(pipeTop);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(bird.getImage(), bird.getx(), bird.gety(), bird.getwidth(), bird.getheight(), null);
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImage(), pipe.getx(), pipe.gety(), pipe.getwidth(), pipe.getheight(), null);
        }
    }

    public void update() {
        bird.fall();
        for (Pipe pipe: pipes)
            pipe.move();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Space key pressed");
            bird.jump(JUMP_FORCE);
        }
    }   
    @Override
    public void keyReleased(KeyEvent e) {}
}
