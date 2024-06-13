package PvZ.PvZ_Manager.Zombie;

import javax.imageio.ImageIO;
import Controller.Audio.AudioManager;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import PvZ.PvZ_Manager.Plant.Plants;
import java.util.HashMap;
import java.util.Map;

public class MaleZombie extends Zombie {
    private static final Map<String, BufferedImage[]> cachedImages = new HashMap<>();
    private BufferedImage[] normalZombieMoveImage;
    private BufferedImage[] normalZombieAttackImage;
    private BufferedImage[] MaleZombieDead;
    private int moveIndex = 0;
    private int attackIndex = 0;
    private int deadIndex = 0;
    private int frameDelay = 2;
    private int frameCounter = 0;
    private boolean isDeadAnimationFinished = false;
    private long lastUpdateTime;

    public MaleZombie(int X, int row) {
        super(X, row);
        setHP(100);
        setSpeed(2);
        setDamge(25);
        setAttackWaitingTime(1000);
        setCollide(false);
        lastUpdateTime = System.nanoTime();
        loadImages();
    }

    private void loadImages() {
        normalZombieMoveImage = loadAndCacheImages("move", "/Resource/Zombie/MaleZombie/ZombieMove/Walk (%d).png", 10);
        normalZombieAttackImage = loadAndCacheImages("attack", "/Resource/Zombie/MaleZombie/ZombieAttack/Attack (%d).png", 8);
        MaleZombieDead = loadAndCacheImages("dead", "/Resource/Zombie/MaleZombie/ZombieDead/Dead (%d).png", 12);
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
        if (this.getRow() == plants.getRow() && this.getX() - plants.getX() >= 0 && this.getX() - plants.getX() <= 10) {
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
                    g2.drawImage(normalZombieMoveImage[moveIndex], getX(), getY() - (150 / 2), 110, 150, null);
                    moveIndex = (moveIndex + 1) % normalZombieMoveImage.length;
                } else {
                    g2.drawImage(normalZombieAttackImage[attackIndex], getX(), getY() - (150 / 2), 110, 150, null);
                    attackIndex = (attackIndex + 1) % normalZombieAttackImage.length;
                }
            } else {
                if (deadIndex < MaleZombieDead.length) {
                    g2.drawImage(MaleZombieDead[deadIndex], getX(), getY() - (150 / 2), 110, 150, null);
                    deadIndex++;
                } else {
                    isDeadAnimationFinished = true;
                }
            }
        } else {
            if (this.getHP() > 0) {
                if (!isCollide()) {
                    g2.drawImage(normalZombieMoveImage[moveIndex], getX(), getY() - (150 / 2), 110, 150, null);
                } else {
                    g2.drawImage(normalZombieAttackImage[attackIndex], getX(), getY() - (150 / 2), 110, 150, null);
                }
            } else {
                if (deadIndex < MaleZombieDead.length) {
                    g2.drawImage(MaleZombieDead[deadIndex], getX(), getY() - (150 / 2), 110, 150, null);
                }
            }
        }
    }

    public boolean isDeadAnimationFinished() {
        return isDeadAnimationFinished;
    }
}
