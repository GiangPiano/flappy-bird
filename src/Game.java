
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {
    // BACKGROUND
    private final Image backgroundImage = new ImageIcon("../assets/background.jpg").getImage();
    public static final int GAME_WIDTH = 360;
    public static final int GAME_HEIGHT = 640;
    // FPS
    private final int FPS = 90;
    Timer gameLoop;
    // BIRD
    private final Image birdImage = new ImageIcon("../assets/bird.png").getImage();
    private final Bird bird;
    // PIPE
    private final Image pipeTopImage = new ImageIcon("../assets/spiketop.png").getImage();
    private final Image pipeBottomImage = new ImageIcon("../assets/spikebottom.png").getImage();
    private ArrayList<Pipe> topPipes;
    private ArrayList<Pipe> bottomPipes;
    private int PIPE_TIME = 1500;
    private int PIPE_GAP = 200;
    private double PIPE_SPEED = 3;
    private int point = 0;
    Timer pipeLoop;
    // PHYSICS
    private boolean isGameOver = false;
    private final int JUMP_FORCE = -10;
    private final double GRAVITY = 0.5;
    Timer difficultyLoop;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); 
        initializeKeyListener();
        
        bird = new Bird(birdImage, GAME_WIDTH / 8, GAME_HEIGHT / 2, 40, 28);
        bird.setGravity(GRAVITY);
        
        topPipes = new ArrayList<>();
        bottomPipes = new ArrayList<>();
        Pipe.speed = (int) PIPE_SPEED;

        pipeLoop = new Timer(PIPE_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipe();
            }
        });
        pipeLoop.start();

        gameLoop = new Timer(1000/FPS, this);
        gameLoop.start();

        difficultyLoop = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PIPE_SPEED < 6) {
                    PIPE_SPEED += 0.05;
                    Pipe.speed = (int) PIPE_SPEED;
                }
                if (PIPE_TIME > 500){
                    PIPE_TIME -= 10;
                    pipeLoop.setDelay(PIPE_TIME);
                }
                if (PIPE_GAP > 80)
                    PIPE_GAP -= 5;
            }
        });
        difficultyLoop.start();
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
        Random random = new Random();
        int pipeY = random.nextInt(GAME_HEIGHT * 1/4, GAME_HEIGHT *3/4);
        Pipe pipeTop = new Pipe(pipeTopImage, GAME_WIDTH, -pipeY, 64, 512);
        Pipe pipeBottom = new Pipe(pipeBottomImage, GAME_WIDTH, -pipeY + 512 + PIPE_GAP, 64, 512);
        topPipes.add(pipeTop);
        bottomPipes.add(pipeBottom);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(bird.getImage(), bird.getx(), bird.gety(), bird.getwidth(), bird.getheight(), null);
        for (Pipe pipe : topPipes)
            g.drawImage(pipe.getImage(), pipe.getx(), pipe.gety(), pipe.getwidth(), pipe.getheight(), null);
        for (Pipe pipe : bottomPipes)
            g.drawImage(pipe.getImage(), pipe.getx(), pipe.gety(), pipe.getwidth(), pipe.getheight(), null);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("SF Pixelate", Font.BOLD, 20));
        if (isGameOver){
            g.drawString("Game over: " + point, 10, 20);
            g.drawString("Press R to restart", 10, 40);
        }
        else
            g.drawString("Score: " + point, 10, 20);
    }

    public boolean collision(Bird bird, Pipe pipe) {
        return bird.getx() < pipe.getx() + pipe.getwidth()/2&&
        bird.getx() + bird.getwidth() > pipe.getx() + pipe.getwidth()/2 &&
        bird.gety() < pipe.gety() + pipe.getheight() &&
        bird.gety() + bird.getheight() > pipe.gety();
    }

    public void update() {
        bird.fall();
        for (Pipe pipe: topPipes){
            if (!pipe.isPassed() && bird.getx() > pipe.getx() + pipe.getwidth()) {
                pipe.setPassed(true);
                point++;
            }
            if (collision(bird, pipe)) {
                System.out.println("Top collision detected");
                isGameOver = true;
            }
            pipe.move();
        }
        for (Pipe pipe: bottomPipes){
            if (collision(bird, pipe)) {
                System.out.println("Bottom collision detected");
                isGameOver = true;
            }
            pipe.move();
        }

        if (bird.gety() >= GAME_HEIGHT - bird.getheight())
            isGameOver = true;
        if (isGameOver) {
            gameLoop.stop();
            pipeLoop.stop();
            difficultyLoop.stop();
        }
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
        if (isGameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                Runner.gameOver();
            }
        }
    }   
    @Override
    public void keyReleased(KeyEvent e) {}
}
