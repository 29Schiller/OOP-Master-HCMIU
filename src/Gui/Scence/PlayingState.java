package Gui.Scence;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

import Controller.Audio.AudioManager;
import Controller.PvZ.LawnManager;
import Controller.PvZ.PlantsManager;
import Controller.PvZ.ZombieManager;
import Controller.Scene.SceneManager;
import Gui.Component.Plants_Bar;
import Gui.Component.TitleLevelGame;
import Gui.Time.GameLoop;
import PvZ.PvZ_Manager.Plant.Shovel;
import PvZ.PvZ_Manager.Zombie.FlagMeter;
import PvZ.PvZ_Manager.Zombie.Zombie;
import PvZ.Sun.SunDrop;

public class PlayingState extends JPanel {
    private SunDrop sunDrop;
    private Shovel shovel;
    private PlantsManager plantsManager;
    private ZombieManager zombieManager;
    private Plants_Bar optionPlants;
    private LawnManager lawnManager;
    private GameLoop gameLoop;
    private Map bg;
    private FlagMeter flag;
    private TitleLevelGame titleLevelGame;
    public static Random random = new Random();
    private Timer sunTimer;
    private Timer sunMovementTimer;
    private Timer lawnActionTimer;
    private Timer zombieSpawnTimer;
    private Timer zombieActionTimer;
    private static boolean gameEnded = false;
    private boolean titleDrawn = false;
    public AudioManager sound = new AudioManager();

    public PlayingState(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        initializeGameComponents();
    }

    // Restart New Game
    public void initializeGameComponents() {
        sunDrop = new SunDrop();
        optionPlants = new Plants_Bar();
        lawnManager = new LawnManager();
        plantsManager = new PlantsManager();
        zombieManager = new ZombieManager();
        ZombieManager.resetStaticVariables();
        zombieManager.updateMaxZombies();
        bg = new Map(this);
        flag = new FlagMeter(zombieManager);
        titleLevelGame = new TitleLevelGame();
        titleLevelGame.showTitleForDuration(4);
        shovel = new Shovel(plantsManager, optionPlants);
        gameEnded = false;
        titleDrawn = false;
    }

    public void stopGame() {
        if (sunTimer != null) {
            sunTimer.stop();
        }
        if (sunMovementTimer != null) {
            sunMovementTimer.stop();
        }
        if (lawnActionTimer != null) {
            lawnActionTimer.stop();
        }
        if (zombieSpawnTimer != null) {
            zombieSpawnTimer.stop();
        }
        if (zombieActionTimer != null) {
            zombieActionTimer.stop();
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

    // User Mouse Click
    public void handleMouseClick(int mouseX, int mouseY) {
        sunDrop.handleSunClick(mouseX, mouseY);
        if (!optionPlants.isChoose()) {
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
        if (!shovel.isChoose()) {
            shovel.chooseShovel(mouseX, mouseY);
        } else {
            shovel.chooseShovel(mouseX, mouseY);
            if (shovel.isChoose()) {
                shovel.RemovePlant(mouseX, mouseY);
            }
        }
    }

    // Game Action
    public void startSunSpawner() {
        sunTimer = new Timer(2000 + random.nextInt(2500), e -> {
            synchronized (sunDrop) {
                sunDrop.spawnSun();
            }
            repaint();
        });
        sunTimer.start();

        sunMovementTimer = new Timer(125, e -> {
            synchronized (sunDrop) {
                sunDrop.sunMove();
            }
            repaint();
        });
        sunMovementTimer.start();
    }

    public void LawnAction() {
        lawnManager.SpawnLawn();
        lawnActionTimer = new Timer(16, e -> {
            synchronized (zombieManager.ZombieList()) {
                lawnManager.Action(zombieManager.ZombieList());
                lawnManager.LawnDead();
            }
            repaint();
        });
        lawnActionTimer.start();
    }

    public void startCharacterSpawnAndAction() {
        int maxZombies = ZombieManager.getLevel() * 10;

        zombieSpawnTimer = new Timer(new Random().nextInt(5000) + 2000, e -> {
            synchronized (zombieManager.ZombieList()) {
                if (gameEnded) {
                    zombieSpawnTimer.stop();
                    return;
                }
                zombieManager.waveZombie();
                if (zombieManager.ZombieList().isEmpty() && ZombieManager.getCountZombie() >= maxZombies && !gameEnded) {
                    gameEnded = true;
                    AudioManager.Win();
                    SceneManager.setGameScenes(SceneManager.WIN);
                    gameLoop.repaint();
                }
            }
        });
        zombieSpawnTimer.start();

        zombieActionTimer = new Timer(16, e -> { //60 fps
            synchronized (zombieManager.ZombieList()) {
                if (gameEnded) {
                    zombieActionTimer.stop();
                    return;
                }
                ArrayList<Zombie> zombiesCopy = new ArrayList<>(zombieManager.ZombieList());
                plantsManager.PlantsAction(zombiesCopy, sunDrop);
                zombieManager.ZombieAction(plantsManager.getPlantsList());
                zombieManager.ZombieDead();
                plantsManager.DeadPlant();
                for (Zombie zombie : zombieManager.ZombieList()) {
                    if (zombie.getX() <= 150) {
                        gameEnded = true;
                        AudioManager.CrazyDaveScream();
                        AudioManager.Lose();
                        SceneManager.setGameScenes(SceneManager.LOOSE);
                        gameLoop.repaint();
                        break;
                    }
                }
            }
            repaint();
        });
        zombieActionTimer.start();
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
