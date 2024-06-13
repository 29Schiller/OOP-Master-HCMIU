package Controller.Scene;

public enum SceneManager {
    MENU, LEVEL, PLAYING, WIN, LOOSE;
    public static SceneManager gameScenes = SceneManager.MENU;
    public static void setGameScenes(SceneManager scene) {
        gameScenes = scene;
    }
}
