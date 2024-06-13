package Gui.Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;
import Controller.Font.CustomFont;
import Controller.PvZ.ZombieManager;

public class TitleLevelGame {
    private boolean showTitle = false;

    public void showTitleForDuration(int durationInSeconds) {
        showTitle = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showTitle = false;
                timer.cancel();
            }
        }, durationInSeconds * 1000);
    }

    public void render(Graphics2D g2) {
        if (showTitle==true) {
            Font font1 = CustomFont.getFont(40f);
            g2.setFont(font1);
            g2.setColor(Color.BLACK);
            g2.drawString("THE LEVEL IS: " + ZombieManager.getLevel() + " (BE CAREFUL !!!)", 500, 150);
        }
    }
}