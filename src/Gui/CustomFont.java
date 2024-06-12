package Gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
public class CustomFont {
    private static Font gameFont;

    static {
        try {
            InputStream fontFile = CustomFont.class.getResourceAsStream("/Resource/Font/FbUsv8C5eI.ttf");
            if (fontFile == null) {
                throw new IOException("Font file not found");
            }
            gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static Font getFont(float size) {
        return gameFont.deriveFont(size);
    }
}