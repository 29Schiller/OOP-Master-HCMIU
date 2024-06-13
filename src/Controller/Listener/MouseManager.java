package Gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter{
    private MenuState menuGame;
    private LooseState loose;
    private Playing playing;
    private WinState win;
    private GameLoop gameLoop;
    private LevelScenes level;
    
    public MouseManager(GameLoop gameLoop, MenuState menuGame,LevelScenes level,Playing playing, LooseState loose,WinState win) {
        this.gameLoop = gameLoop;
        this.menuGame = menuGame;
        this.loose = loose;
        this.playing=playing;
        this.win=win;
        this.level=level;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (GameScenes.gameScenes == GameScenes.MENU) {
            menuGame.handleMouseClick(mouseX, mouseY);
        }
        else if (GameScenes.gameScenes == GameScenes.LEVEL) {
            level.handleMouseClick(mouseX, mouseY);
        }
        else if(GameScenes.gameScenes==GameScenes.PLAYING){
            playing.handleMouseClick(mouseX, mouseY);
        }
        else if (GameScenes.gameScenes == GameScenes.LOOSE) {
            loose.handleMouseClick(mouseX, mouseY);
        }
        else if (GameScenes.gameScenes == GameScenes.WIN) {
            win.handleMouseClick(mouseX, mouseY);
        }
    }
}
