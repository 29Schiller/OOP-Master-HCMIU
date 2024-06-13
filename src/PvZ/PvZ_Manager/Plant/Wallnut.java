package PvZ.PvZ_Manager.Plant;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import PvZ.PvZ_Manager.Zombie.Zombie;
import PvZ.Sun.SunDrop;

public class Wallnut extends Plants{
     public Wallnut(int X, int row, boolean frozzen) {
        super(X, row, frozzen);
        setHP(250);
        setDamage(0);
        setPrice(75);
        setFrozzen(false);
        importImage();
    }

    private int timeIndex=0;
    private int frameDelay = 3;
    private int frameCounter = 0;
    private BufferedImage[] WallNutImage=new BufferedImage[12];

    @Override
    public void importImage() {
        for(int i=0; i<WallNutImage.length;i++){
            try {WallNutImage[i]=ImageIO.read(getClass().getResourceAsStream("/Resource/Plants/Wallnut/"+(i+1)+".png"));
            } catch (IOException e) {e.printStackTrace();}}
    }

    @Override
    public void Action(Zombie zombie,SunDrop sunDrop) {
    }

    @Override
    public void renderPlantsAction(Graphics2D g2,List<Zombie> zombieList) {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
        g2.drawImage(WallNutImage[timeIndex], getX() +15, getY(), 70, 80, null);
        timeIndex = (timeIndex + 1) % WallNutImage.length;
        }
        else{
            g2.drawImage(WallNutImage[timeIndex], getX() +15, getY(), 70, 80, null);
        }
    }
}
