package Gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GameLoop extends JPanel implements Runnable {
    private Boolean isRunning;
    private final long FPS = 60;
    private final long PERIOD = 1000 * 1000000 / FPS; 
    private MenuState menuGame;
    private Playing playing;
    private LooseState loose;
    private WinState win;
    private LevelScenes level;
     public GameLoop() {
        isRunning = true;
        initclasses();
        MouseManager mouseListener = new MouseManager(this, menuGame,level,playing, loose,win);
        this.addMouseListener(mouseListener);
    }

    public void stop() {
        isRunning = false;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public MenuState getMenuGame() {
        return menuGame;    
    }

    public Playing getPlaying() {
        return playing;
    }

    public LooseState getOverGame() {
        return loose;
    }
    public WinState getWinState() {
        return win;
    }
    public LevelScenes getLevelstate() {
        return level;
    }

    public void initclasses() {
        menuGame = new MenuState(this);
        level=new LevelScenes(this);
        playing = new Playing(this);
        loose = new LooseState(this);
        win =new WinState(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        render(g2);
    }
    public void render(Graphics2D g2) {
        switch (GameScenes.gameScenes) {
            case MENU:
                menuGame.render(g2);
                break;
            case LEVEL:
                level.render(g2);
                break;
            case PLAYING:
                playing.render(g2);
                break;
            case LOOSE:
                loose.render(g2);
                break;
            case WIN:
                win.render(g2);
                break;
        }
    }
    @Override
    public void run() {
        long beginTime;
        long sleepTime;
        beginTime = System.nanoTime();
        while (isRunning) {
            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = PERIOD - deltaTime;
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000);
                } else {
                    Thread.sleep(PERIOD / 2000000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            repaint();
            beginTime = System.nanoTime();
        }
    }
}
