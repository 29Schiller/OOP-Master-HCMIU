package Gui.Scence;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import Controller.PvZ.ZombieManager;
import Controller.Scene.SceneManager;
import Gui.Time.GameLoop;

public class LevelState {
    private GameLoop gameLoop;
    private BufferedImage levelImage;
    private BufferedImage[] level=new BufferedImage[6];
    private Rectangle bounds1,bounds2,bounds3,bounds4,bounds5,bounds6;
    public LevelState(GameLoop gameLoop){
        this.gameLoop=gameLoop;
        bounds1=new Rectangle(555,280,80,80);
        bounds2=new Rectangle(685,280,80,80);
        bounds3=new Rectangle(820,280,80,80);
        bounds4=new Rectangle(555,370,80,80);
        bounds5=new Rectangle(690,370,80,80);
        bounds6=new Rectangle(815,370,80,80);
        importImage();
    }
    public void importImage(){
        try {levelImage=ImageIO.read(getClass().getResourceAsStream("/Resource/LevelChoose/LevelGame.png"));
        } catch (IOException e) {e.printStackTrace();}
        for (int i=0;i<level.length;i++){
            try {level[i]=ImageIO.read(getClass().getResourceAsStream("/Resource/LevelChoose/Level "+(i+1)+".png"));
            } catch (IOException e) {e.printStackTrace();}
        }
    }
    public void handleMouseClick(int mouseX, int mouseY) {
        if(this.getBounds1().contains(mouseX,mouseY)){
            ZombieManager.setLevel(1);
            gameLoop.getPlaying().resetGame();
            SceneManager.setGameScenes(SceneManager.PLAYING); 
            gameLoop.repaint(); 
        }
        else if (this.getBounds2().contains(mouseX,mouseY)){
            ZombieManager.setLevel(2);
            gameLoop.getPlaying().resetGame();
            SceneManager.setGameScenes(SceneManager.PLAYING); 
            gameLoop.repaint(); 
        }
        else if (this.getBounds3().contains(mouseX,mouseY)){
            ZombieManager.setLevel(3);
            gameLoop.getPlaying().resetGame();
            SceneManager.setGameScenes(SceneManager.PLAYING); 
            gameLoop.repaint(); 
        }
        else if (this.getBounds4().contains(mouseX,mouseY)){
            ZombieManager.setLevel(4);
            gameLoop.getPlaying().resetGame();
            SceneManager.setGameScenes(SceneManager.PLAYING); 
            gameLoop.repaint(); 
        }
        else if (this.getBounds5().contains(mouseX,mouseY)){
            ZombieManager.setLevel(5);
            gameLoop.getPlaying().resetGame();
            SceneManager.setGameScenes(SceneManager.PLAYING); 
            gameLoop.repaint(); 
        }
        else if (this.getBounds6().contains(mouseX,mouseY)){
            ZombieManager.setLevel(6);
            gameLoop.getPlaying().resetGame();
            SceneManager.setGameScenes(SceneManager.PLAYING); 
            gameLoop.repaint(); 
        } 
    }
    public void render(Graphics2D g2){
        g2.drawImage(levelImage, 0,0,1300,750, null);
        g2.drawImage(level[0], bounds1.x,bounds1.y,bounds1.width,bounds1.height, null);
        g2.drawImage(level[1], bounds2.x,bounds2.y,bounds2.width,bounds2.height, null);
        g2.drawImage(level[2], bounds3.x,bounds3.y,bounds3.width,bounds3.height, null);
        g2.drawImage(level[3], bounds4.x,bounds4.y,bounds4.width,bounds4.height, null);
        g2.drawImage(level[4], bounds5.x,bounds5.y,bounds5.width,bounds5.height, null);
        g2.drawImage(level[5], bounds6.x,bounds6.y,bounds6.width,bounds6.height, null);
    }
    public Rectangle getBounds1() {return bounds1;}
    public Rectangle getBounds2() {return bounds2;}
    public Rectangle getBounds3() {return bounds3;}
    public Rectangle getBounds4() {return bounds4;}
    public Rectangle getBounds5() {return bounds5;}
    public Rectangle getBounds6() {return bounds6;}
    
}
