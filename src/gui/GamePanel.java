package gui;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel  implements Runnable{
    long beginTime;
    long sleepTime;
    long FPS = 80;
    long PERIOD = 1000 * 1000000 / FPS;

    int a = 1;
    private Thread thread;
    private Boolean isRunning;

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        g.setColor(Color.RED);
        g.fillRect(0,0,750,750);
    }
    
    public void startgame(){
        if (thread == null){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        beginTime = System.nanoTime();
        While(isRunning);
    }

    private void While(Boolean exBoolean) {
        //update
        //render

        long deltaTime = System.nanoTime() - beginTime;
        sleepTime = PERIOD - deltaTime;
        try {
            if (sleepTime > 0) {
                Thread.sleep(sleepTime / 1000000);
            } else {
                Thread.sleep(PERIOD / 2000000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        beginTime = System.nanoTime();
    }

}