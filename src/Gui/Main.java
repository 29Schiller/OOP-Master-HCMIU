package Gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import Gui.Time.GameLoop;

public class Main extends JFrame{
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;

    public Main() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static void main(String[] args) {
        Main gameFrame = new Main();
        GameLoop gameLoop = new GameLoop();
        gameFrame.add(gameLoop);
        gameFrame.setVisible(true);
        gameLoop.start();  // Start the game loop
    }
}
