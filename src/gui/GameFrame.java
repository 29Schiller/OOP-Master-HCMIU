
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.*;// Example
import javax.swing.JPanel;// Example

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Graphics.GamePanel;

public class GameFrame extends JFrame implements MouseListener {
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;

    //Example
    ImageIcon smile;
    ImageIcon nervous;
    ImageIcon pain;
    ImageIcon dizzy;
    JLabel label;
    //Example

    GamePanel gamePanel;
    GameLoop gameLoop;

    public GameFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        gameLoop = new GameLoop();
        add(gamePanel);

        //Example
        smile = new ImageIcon("Smile.png");
        nervous = new ImageIcon("nervous.png");
        pain = new ImageIcon("pain.png");
        dizzy = new ImageIcon("Dizzy.png");
        label = new JLabel();
        label.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        label.addMouseListener(this);
        //Example
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public void startGame() {
        Thread gameThread = new Thread(gameLoop);
        gameThread.start();
    }

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
        gameFrame.startGame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        label.setIcon(nervous);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        label.setIcon(smile);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        label.setIcon(pain);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        label.setIcon(dizzy);
    }
}