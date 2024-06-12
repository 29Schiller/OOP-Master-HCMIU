package PvZ.PvZ_Manager.Zombie;

import javax.imageio.ImageIO;
import Gui.AudioManager;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import PvZ.PvZ_Manager.Plant.Plants;
import java.util.HashMap;
import java.util.Map;

public class FemaleZombie extends Zombie {
    private static final Map<String, BufferedImage[]> cachedImages = new HashMap<>();
    private BufferedImage[] FemaleZombieMoveImage;
    private BufferedImage[] FemaleZombieAttackImage;
    private BufferedImage[] FemaleZombieDeadImage;
    private int moveIndex = 0;
    private int attackIndex = 0;
    private int deadIndex = 0;
    private int frameDelay = 2;
    private int frameCounter = 0;
    private boolean isDeadAnimationFinished = false;
    private long lastUpdateTime;

    public FemaleZombie(int X, int row) {
        super(X, row);
        setHP(160);
        setSpeed(2);
        setDamge(25);
        setAttackWaitingTime(1000);
        setCollide(false);
        lastUpdateTime = System.nanoTime();
        loadImages();
    }

    private void loadImages() {
        FemaleZombieMoveImage = loadAndCacheImages("move", "/Resource/Zombie/FemaleZombie/Walk/Walk (%d).png", 10);
        FemaleZombieAttackImage = loadAndCacheImages("attack", "/Resource/Zombie/FemaleZombie/Attack/Attack (%d).png", 8);
        FemaleZombieDeadImage = loadAndCacheImages("dead", "/Resource/Zombie/FemaleZombie/Dead/Dead (%d).png", 12);
    }

    private BufferedImage[] loadAndCacheImages(String key, String pathTemplate, int count) {
        if (!cachedImages.containsKey(key)) {
            BufferedImage[] images = new BufferedImage[count];
            for (int i = 0; i < count; i++) {
                try {
                    images[i] = ImageIO.read(getClass().getResourceAsStream(String.format(pathTemplate, i + 1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cachedImages.put(key, images);
        }
        return cachedImages.get(key);
    }

    @Override
    public void move() {
        if (!this.isCollide() && getX() >= 150) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // Convert to seconds
            setX((int) (getX() - getSpeed() * deltaTime * 5)); // Adjust position based on delta time
            lastUpdateTime = currentTime; // Update the last update time
        }
    }

    @Override
    public void CollidePlants(Plants plants) {
        if (this.getRow() == plants.getRow() && this.getX() - plants.getX() >= 0 && this.getX() - plants.getX() <= 20) {
            this.setCollide(true);
        }
    }

    @Override
    public void attack(Plants plants) {
        long currentTime = System.currentTimeMillis();
        if (isCollide() && (currentTime - getLastAttackTime()) >= getAttackWaitingTime()) {
            plants.setHP(plants.getHP() - this.getDamage());
            AudioManager.ZombieEat();
            setLastAttackTime(currentTime);
        }
    }

    @Override
    public void winplants(Plants plants) {
        if (plants.getHP() <= 0) {
            setCollide(false);
        }
    }

    @Override
    public void renderZombiesAction(Graphics2D g2) {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            if (this.getHP() > 0) {
                if (!isCollide()) {
                    g2.drawImage(FemaleZombieMoveImage[moveIndex], getX(), getY() - (150 / 2), 110, 150, null);
                    moveIndex = (moveIndex + 1) % FemaleZombieMoveImage.length;
                } else {
                    g2.drawImage(FemaleZombieAttackImage[attackIndex], getX(), getY() - (150 / 2), 110, 150, null);
                    attackIndex = (attackIndex + 1) % FemaleZombieAttackImage.length;
                }
            } else {
                if (deadIndex < FemaleZombieDeadImage.length) {
                    g2.drawImage(FemaleZombieDeadImage[deadIndex], getX(), getY() - (150 / 2), 110, 150, null);
                    deadIndex++;
                } else {
                    isDeadAnimationFinished = true;
                }
            }
        } else {
            if (this.getHP() > 0) {
                if (!isCollide()) {
                    g2.drawImage(FemaleZombieMoveImage[moveIndex], getX(), getY() - (150 / 2), 110, 150, null);
                } else {
                    g2.drawImage(FemaleZombieAttackImage[attackIndex], getX(), getY() - (150 / 2), 110, 150, null);
                }
            } else {
                if (deadIndex < FemaleZombieDeadImage.length) {
                    g2.drawImage(FemaleZombieDeadImage[deadIndex], getX(), getY() - (150 / 2), 110, 150, null);
                }
            }
        }
    }

    public boolean isDeadAnimationFinished() {
        return isDeadAnimationFinished;
    }
}
