package Gui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MenuState {
    private GameLoop gameLoop;
    private BufferedImage menuGame;
    private BufferedImage pvz_logo;
    private BufferedImage playButton;
    private Rectangle bounds;
    public AudioManager sound = new AudioManager();

    public MenuState(GameLoop gameLoop){
        this.gameLoop=gameLoop;
        bounds=new Rectangle(400,500,500,100);
        importImage();
        AudioManager.play_Theme();
    }
    public void importImage(){
        try {menuGame=ImageIO.read(getClass().getResourceAsStream("/Resource/MenuGame/wallpaper.jpg"));
        } catch (IOException e) {e.printStackTrace();}
        try {pvz_logo=ImageIO.read(getClass().getResourceAsStream("/Resource/MenuGame/pvz_logo.png"));
        } catch (IOException e) {e.printStackTrace();}
        try {playButton=ImageIO.read(getClass().getResourceAsStream("/Resource/MenuGame/PlayDemo.png"));
        } catch (IOException e) {e.printStackTrace();}
    }
    public void render(Graphics2D g2){
        g2.drawImage(menuGame,0,0,1300,750,null);
        g2.drawImage(pvz_logo, 300,50,700,200, null);
        g2.drawImage(playButton,bounds.x,bounds.y,bounds.width,bounds.height, null);
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void handleMouseClick(int mouseX, int mouseY) {
        if(this.getBounds().contains(mouseX,mouseY)){
            gameLoop.getLevelstate();
            GameScenes.setGameScenes(GameScenes.LEVEL); 
            gameLoop.repaint();
        }
    }

}
