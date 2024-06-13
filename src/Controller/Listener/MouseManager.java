package Controller.Listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Controller.Scene.SceneManager;
import Gui.Scence.*;
import Gui.Time.*;

public class MouseManager extends MouseAdapter {
    private MenuState menuGame;
    private LoseState loose;
    private PlayingState playing;
    private WinState win;
    private GameLoop gameLoop;
    private LevelState level;

    public MouseManager(GameLoop gameLoop, MenuState menuGame, LevelState level, PlayingState playing, LoseState loose, WinState win) {
        this.gameLoop = gameLoop;
        this.menuGame = menuGame;
        this.loose = loose;
        this.playing = playing;
        this.win = win;
        this.level = level;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (SceneManager.gameScenes == SceneManager.MENU) {
            menuGame.handleMouseClick(mouseX, mouseY);
        } else if (SceneManager.gameScenes == SceneManager.LEVEL) {
            level.handleMouseClick(mouseX, mouseY);
        } else if (SceneManager.gameScenes == SceneManager.PLAYING) {
            playing.handleMouseClick(mouseX, mouseY);
        } else if (SceneManager.gameScenes == SceneManager.LOOSE) {
            loose.handleMouseClick(mouseX, mouseY);
        } else if (SceneManager.gameScenes == SceneManager.WIN) {
            win.handleMouseClick(mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (SceneManager.gameScenes == SceneManager.LEVEL) {
            level.handleMouseMove(mouseX, mouseY);
        }
    }
}
