package Gui.Scence;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Controller.Font.CustomFont;
import Controller.PvZ.ZombieManager;
import Controller.Scene.SceneManager;
import Gui.Time.GameLoop;

public class LevelState extends JPanel {
    private GameLoop gameLoop;
    private BufferedImage lvImage;
    private Ellipse2D[] buttonBounds = new Ellipse2D[6];
    private int[] buttonX = {555, 685, 820, 555, 685, 820};
    private int[] buttonY = {280, 280, 280, 420, 420, 420};
    private int buttonRadius = 40;
    private int hoveredButton = -1;

    public LevelState(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        setLayout(null);
        for (int i = 0; i < 6; i++) {
            buttonBounds[i] = new Ellipse2D.Float(buttonX[i], buttonY[i], 2 * buttonRadius, 2 * buttonRadius);
        }
        importImage();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMove(e.getX(), e.getY());
            }
        });
    }

    public void importImage() {
        try {
            lvImage = ImageIO.read(getClass().getResourceAsStream("/Resource/LevelChoose/Lv.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMouseClick(int mouseX, int mouseY) {
        for (int i = 0; i < buttonBounds.length; i++) {
            if (buttonBounds[i].contains(mouseX, mouseY)) {
                ZombieManager.setLevel(i + 1);
                gameLoop.getPlaying().resetGame();
                SceneManager.setGameScenes(SceneManager.PLAYING);
                gameLoop.repaint();
                break;
            }
        }
    }

    public void handleMouseMove(int mouseX, int mouseY) {
        boolean found = false;
        for (int i = 0; i < buttonBounds.length; i++) {
            if (buttonBounds[i].contains(mouseX, mouseY)) {
                hoveredButton = i;
                found = true;
                break;
            }
        }
        if (!found) {
            hoveredButton = -1;
        }
        repaint();
    }

    public void render(Graphics2D g2) {
        // Draw the background image
        g2.drawImage(lvImage, 0, 0, 1300, 750, null);

        // Draw the buttons
        for (int i = 0; i < buttonBounds.length; i++) {
            drawButton(g2, buttonBounds[i], String.valueOf(i + 1), i == hoveredButton);
        }
    }

    private void drawButton(Graphics2D g2, Ellipse2D button, String label, boolean isHovered) {
        g2.setColor(new Color(246, 239, 211)); // #f6efd3
        g2.fill(button);

        if (isHovered) {
            g2.setColor(new Color(187, 240, 197, 255)); // #bbf0c5
            g2.fill(button);
            g2.setStroke(new BasicStroke(8));
            g2.setColor(new Color(36, 86, 72)); // #245648
        } else {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(new Color(36, 86, 72)); // #245648
        }
        g2.draw(button);

        Font buttonFont;
        if (isHovered) {
            buttonFont = CustomFont.getFont(45);
        } else {
            buttonFont = CustomFont.getFont(40);
        }
        g2.setFont(buttonFont);
        g2.setColor(Color.BLACK);

        int stringLen = (int) g2.getFontMetrics().getStringBounds(label, g2).getWidth();
        int stringHeight = (int) g2.getFontMetrics().getStringBounds(label, g2).getHeight();
        int startX = (int) (button.getX() + (button.getWidth() - stringLen) / 2);
        int startY = (int) (button.getY() + (button.getHeight() + stringHeight) / 2 - 10);
        g2.drawString(label, startX, startY);
    }

}
