package Controller.PvZ;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import Gui.Component.TitleLevelGame;
import Gui.Scence.PlayingState;
import PvZ.PvZ_Manager.Plant.Plants;
import PvZ.PvZ_Manager.Zombie.FemaleZombie;
import PvZ.PvZ_Manager.Zombie.MaleZombie;
import PvZ.PvZ_Manager.Zombie.Zombie;

public class ZombieManager {
    Plants plants;
    private static int level;
    private List<Zombie> zombieList = new CopyOnWriteArrayList<>();
    private static int CountZombie = 0;
    private static int Points = 0;
    private TitleLevelGame LevelBoard;
    private int maxZombies;
    private Random random=new Random();
    public ZombieManager() {
        LevelBoard = new TitleLevelGame();
    }

    public void SpawnZombie(int type) {
        if (PlayingState.isGameEnded()) {
            return;
        }
        int Startrow = PlayingState.random.nextInt(5) + 1;
        if (type == 0) {
            zombieList.add(new MaleZombie(1300, Startrow));
            setCountZombie(getCountZombie() + 1);
            Points += 10;
        } else if (type == 1) {
            zombieList.add(new FemaleZombie(1300, Startrow));
            setCountZombie(getCountZombie() + 1);
            Points += 25;
        }
    }

    public void waveZombie(){
        if (ZombieManager.getCountZombie() < maxZombies) {
            if(ZombieManager.getCountZombie()<=10){
                this.SpawnZombie(random.nextInt(2));
            }
            else if(10<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=20){
                for (int i=0;i<2;i++){
                    this.SpawnZombie(random.nextInt(2));
                }
            }
            else if(20<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=30){
                for (int i=0;i<3;i++){
                    this.SpawnZombie(random.nextInt(2));
                }
            }
            else if(30<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=40){
                for (int i=0;i<4;i++){
                    this.SpawnZombie(random.nextInt(2));
                }
            }
            else if(40<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=50){
                for (int i=0;i<5;i++){
                    this.SpawnZombie(random.nextInt(2));
                }
            }
            else if(50<ZombieManager.getCountZombie()&&ZombieManager.getCountZombie()<=60){
                for (int i=0;i<10;i++){
                    this.SpawnZombie(random.nextInt(2));
                }
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

    public static void setLevel(int level) {
        ZombieManager.level = level;
    }

    public void updateMaxZombies() {
        maxZombies = ZombieManager.getLevel() * 10;
    }

    public void render(Graphics2D g2) {
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
        setCountZombie(0);
        setPoints(0);
    }
}