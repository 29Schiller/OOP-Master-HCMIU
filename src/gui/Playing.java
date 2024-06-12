package Gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import PvZ.PvZ_Manager.Lawn.LawnManager;
import PvZ.PvZ_Manager.Plant.OptionPlants;
import PvZ.PvZ_Manager.Plant.PlantsManager;
import PvZ.PvZ_Manager.Plant.Shovel;
import PvZ.PvZ_Manager.Zombie.FlagMeter;
import PvZ.PvZ_Manager.Zombie.TitleLevelGame;
import PvZ.PvZ_Manager.Zombie.Zombie;
import PvZ.PvZ_Manager.Zombie.ZombieManager;
import PvZ.Sun.SunDrop;

public class Playing extends JPanel{
    private SunDrop sunDrop;
    private Shovel shovel;
    private PlantsManager plantsManager;
    private ZombieManager zombieManager;
    private OptionPlants optionPlants;
    private LawnManager lawnManager;
    private GameLoop gameLoop;
    private Map_Background bg;
    private FlagMeter flag;
    private TitleLevelGame titleLevelGame;
    private Random random;
    private Timer sunTimer;
    private Timer sunMovementTimer;
    private Timer lawnActionTimer;
    private Timer zombieSpawnTimer;
    private Timer zombieActionTimer;
    private static boolean gameEnded = false;
    private boolean titleDrawn = false; 
    public AudioManager sound = new AudioManager();

    public Playing(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        initializeGameComponents();
    }
    // Restart New Game
    public void initializeGameComponents() {
        sunDrop = new SunDrop();
        optionPlants = new OptionPlants();
        lawnManager = new LawnManager();
        plantsManager = new PlantsManager();
        zombieManager = new ZombieManager();
        ZombieManager.resetStaticVariables();
        bg = new Map_Background(this);
        flag=new FlagMeter(zombieManager);
        titleLevelGame = new TitleLevelGame();
        titleLevelGame.showTitleForDuration(4);
        shovel=new Shovel(plantsManager,optionPlants);
        random = new Random();
        gameEnded = false;
        titleDrawn = false;
    }

    public void stopGame() {
        
        if (sunTimer != null) {
            sunTimer.cancel();
        }
        if (sunMovementTimer != null) {
            sunMovementTimer.cancel();
        }
        if (lawnActionTimer != null) {
            lawnActionTimer.cancel();
        }
        if (zombieSpawnTimer != null) {
            zombieSpawnTimer.cancel();
        }
        if (zombieActionTimer != null) {
            zombieActionTimer.cancel();
        }
        gameEnded = false;
    }
    
    public void startGame() {
        startSunSpawner();
        LawnAction();
        startCharacterSpawnAndAction();
        AudioManager.ZombieStart();
    }

    public void resetGame() {
        stopGame();
        initializeGameComponents();
        startGame();
    }

    //User Mouse Click
    public void handleMouseClick(int mouseX, int mouseY) {
        sunDrop.handleSunClick(mouseX, mouseY);
        if (optionPlants.isChoose()==false) {
            optionPlants.handPlantsChoose(mouseX, mouseY);
        } else {
            int previous_X = optionPlants.getXSpwan();
            int previous_Row = optionPlants.getRowSpawn();
            optionPlants.ChoosePositionForPlant(mouseX, mouseY);
            if (previous_X != optionPlants.getXSpwan() || previous_Row != optionPlants.getRowSpawn()) {
                plantsManager.SpawnPlants(optionPlants.getType(), optionPlants.getXSpwan(), optionPlants.getRowSpawn(), sunDrop);
                optionPlants.setChoose(false);
                repaint();
            }
        }
        if(shovel.isChoose()==false){
            shovel.chooseShovel(mouseX, mouseY);
        } else{shovel.chooseShovel(mouseX, mouseY);
                if(shovel.isChoose()==true){
                shovel.RemovePlant(mouseX, mouseY);
            }
        }

    }

    // Game Action
    public void startSunSpawner() {
        sunTimer = new Timer();
        sunTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (sunDrop) {
                    sunDrop.spawnSun();
                }
                repaint();
            }
        }, 0, 2000 + random.nextInt(2500));

        sunMovementTimer = new Timer();
        sunMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (sunDrop) {
                    sunDrop.sunMove();
                }
                repaint();
            }
        }, 0, 125);
        
    }

    public void LawnAction() {
        lawnManager.SpawnLawn();
        lawnActionTimer = new Timer();
        lawnActionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (zombieManager.ZombieList()) {
                    lawnManager.Action(zombieManager.ZombieList());
                    lawnManager.LawnDead();
                }
                repaint();
            }
        }, 0, 125);
    }

    public void startCharacterSpawnAndAction() {
        int maxZombies = ZombieManager.getLevel() * 10;
        zombieSpawnTimer = new Timer();
        zombieSpawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (zombieManager.ZombieList()) {
                    if (gameEnded) {
                        zombieSpawnTimer.cancel();
                        return;
                    }
                    zombieManager.waveZombie();
                    if (zombieManager.ZombieList().isEmpty() && ZombieManager.getCountZombie() >= maxZombies && !gameEnded) {
                        gameEnded = true;
                        AudioManager.Win();
                        GameScenes.setGameScenes(GameScenes.OVERGAME);
                        gameLoop.repaint();
                    }
                }
            }
        }, 0, new Random().nextInt(5000) + 2000);
    
        zombieActionTimer = new Timer();
        zombieActionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (zombieManager.ZombieList()) {
                    if (gameEnded) {
                        zombieActionTimer.cancel();
                        return;
                    }
                    ArrayList<Zombie> zombiesCopy = new ArrayList<>(zombieManager.ZombieList());
                    plantsManager.PlantsAction(zombiesCopy, sunDrop);
                    zombieManager.ZombieAction(plantsManager.getPlantsList());
                    zombieManager.ZombieDead();
                    plantsManager.DeadPlant();
                    for (Zombie zombie : zombieManager.ZombieList()) {
                        if (zombie.getX() <= 20) {
                            gameEnded = true;
                            AudioManager.CrazyDaveScream();
                            AudioManager.Lose();
                            GameScenes.setGameScenes(GameScenes.OVERGAME);
                            gameLoop.repaint();
                            break;
                        }
                    }
                }
                repaint();
            }
        }, 0, 200);
    }

    public void render(Graphics2D g2) {
        bg.render(g2);
        flag.render(g2);
        shovel.render(g2);
        sunDrop.render(g2);
        plantsManager.render(g2, zombieManager.ZombieList(), sunDrop);
        lawnManager.render(g2);
        sunDrop.drawStorage(g2);
        zombieManager.render(g2);
        titleLevelGame.render(g2);
    }
    public static boolean isGameEnded() {
        return gameEnded;
    }
}
