package PvZ.PvZ_Manager.Zombie;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FlagMeter {
    private BufferedImage FlagMeterFull;
    private BufferedImage FlagMeterEmpty;
    private int totalSpawn=ZombieManager.getLevel()*10;
    private int ratio=244/totalSpawn;
    private int FlagMeter=244;
    public FlagMeter(ZombieManager zombieManager){
        zombieManager=new ZombieManager();
        importImage();
    }

    public void importImage(){
        try {FlagMeterFull=ImageIO.read(getClass().getResourceAsStream("/Resource/Zombie/Flag/FlagMeterFull.png"));
        } catch (IOException e) {e.printStackTrace();}
        try {FlagMeterEmpty=ImageIO.read(getClass().getResourceAsStream("/Resource/Zombie/Flag/FlagMeterEmpty.png"));
        } catch (IOException e) {e.printStackTrace();}
    }
    
    public void render(Graphics2D g2){
        g2.drawImage(FlagMeterFull, 1000, 10,250,30,null);
        if(FlagMeter>=0){
            int FlagMeter=244-ratio*ZombieManager.getCountZombie();
            g2.drawImage(FlagMeterEmpty,1003,10,FlagMeter,30 , null);
        }
        else {
            FlagMeter=0;
            g2.drawImage(FlagMeterEmpty,1003,10,FlagMeter,30 , null);
        }
    }
}
