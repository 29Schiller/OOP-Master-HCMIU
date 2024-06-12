package Gui;

public enum GameScenes {
    MENU, PLAYING, WIN, LOOSE;
    public static GameScenes gameScenes = GameScenes.MENU;
    public static void setGameScenes(GameScenes scene) {
        gameScenes = scene;
    }
}
