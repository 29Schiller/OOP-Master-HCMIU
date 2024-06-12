package PvZ.PvZ_Manager.Zombie;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import Gui.Playing;
import PvZ.PvZ_Manager.Plant.Plants;

public class ZombieManager {
    Plants plants;
    private static Random random = new Random();
    private static int level = random.nextInt(6) + 1;
    private List<Zombie> zombieList = new CopyOnWriteArrayList<>();
    private static int CountZombie = 0;
    private static int Points=0;
    private TitleLevelGame LevelBoard;
    public ZombieManager() {
        LevelBoard = new TitleLevelGame();
    }

    public void SpawnZombie(int type) {
        if (Playing.isGameEnded()) {
            return;
        }
        int Startrow = random.nextInt(5) + 1;
        if (type == 0) {
            zombieList.add(new MaleZombie(1300, Startrow));
            setCountZombie(getCountZombie() + 1);
            Points += 10;
        } else if (type == 1) {
            zombieList.add(new FemaleZombie(1300, Startrow));
            setCountZombie(getCountZombie() + 1);
            Points += 20;
        }
    }
    public void waveZombie(){
        int maxZombies = ZombieManager.getLevel() * 10;
        if (ZombieManager.getCountZombie() < maxZombies) {
            if(ZombieManager.getCountZombie()<=10){
            this.SpawnZombie(random.nextInt(2));
            }
            else if(10<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=20){
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
            }
            else if(20<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=30){
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
            }
            else if(30<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=40){
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
            }
            else if(40<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=50){
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
            }
            else if(50<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=60){
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));
                this.SpawnZombie(random.nextInt(2));

            }
        }
    }
    public void ZombieAction(List<Plants> plantsList) {
        for (Zombie zombie : zombieList) {
            Plants closestPlant = null;
            int minDistance = Integer.MAX_VALUE;
            for (Plants plant : plantsList) {
                if (zombie.getRow() == plant.getRow()) {
                    int distance = Math.abs(zombie.getX() - plant.getX());
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestPlant = plant;
                    }
                }
            }
            if (closestPlant != null) {
                zombie.CollidePlants(closestPlant);
                if (zombie.isCollide()) {
                    zombie.setCollide(true);
                    zombie.attack(closestPlant);
                    zombie.winplants(closestPlant);
                } else {
                    zombie.move();
                }
            } else {
                zombie.move();
            }
        }
    }

    public void ZombieDead() {
        for (Zombie zombie : zombieList) {
            if (zombie.getHP() <= 0 && zombie.isDeadAnimationFinished()) {
                zombieList.remove(zombie);
            }
        }
    }

    public List<Zombie> ZombieList() {
        return zombieList;
    }

    public static int getCountZombie() {
        return CountZombie;
    }

    public static void setCountZombie(int countZombie) {
        CountZombie = countZombie;
    }

    public static int getLevel() {
        return level;
    }
    public void render(Graphics2D g2){
        LevelBoard.render(g2);
        synchronized (this.ZombieList()) {
            for (Zombie zombie : new CopyOnWriteArrayList<>(this.ZombieList())) {
                zombie.renderZombiesAction(g2);
            }
        }
    }
    public static int getPoints() {
        return Points;
    }
    public static void setPoints(int points) {
        Points = points;
    }
    public static void resetStaticVariables() {
        CountZombie = 0;
        Points = 0;
        level = random.nextInt(6) + 1;
    }   
}
