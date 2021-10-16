package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Floor {
    private ArrayList<BufferedImage> floorImages;
    private int fHeight;
    private int fWidth;
    private int locX1 = 0;
    private int locX2 = 0;

    public Floor() {
        floorImages = new ArrayList<>();

        try {
            floorImages.add(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/base.png")));
            floorImages.add(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/base.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fHeight = floorImages.get(0).getHeight();
        fWidth = floorImages.get(0).getWidth();
        locX2 = fWidth;
    }

    public void draw(Graphics2D g) {

        g.drawImage(floorImages.get(0), locX1, Screen.HEIGHT - fHeight, fWidth, fHeight, null);
        g.drawImage(floorImages.get(0), locX2, Screen.HEIGHT - fHeight, fWidth, fHeight, null);

    }

    public void scroll() {
        locX1 -= 3;
        locX2 -= 3;
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
