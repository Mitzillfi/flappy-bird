
package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameBG {
    private ArrayList<BufferedImage> bg;
    private int fHeight;
    private int fWidth;
    private int locX1 = 0;
    private int locX2 = 0;

    public GameBG(String day) {
        bg = new ArrayList<>();

        try {
            bg.add(ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/background-" + day + ".png")));
            bg.add(ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/background-" + day + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fHeight = bg.get(0).getHeight();
        fWidth = bg.get(0).getWidth();
        locX2 = fWidth;
    }

    public void draw(Graphics2D g) {

        g.drawImage(bg.get(0), locX1, Screen.HEIGHT - fHeight, fWidth, fHeight, null);
        g.drawImage(bg.get(0), locX2, Screen.HEIGHT - fHeight, fWidth, fHeight, null);

    }

    public void scroll() {
        locX1 -= 2;
        locX2 -= 2;
        if (locX1 <= -fWidth) {
            locX1 = Screen.WIDTH;
        }
        if (locX2 <= -fWidth) {
            locX2 = Screen.WIDTH;
        }
    }

    public int getLocX1() {
        return locX1;
    }

    public int getLocX2() {
        return locX2;
    }
}
