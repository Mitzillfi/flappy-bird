package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bar {
    private final BufferedImage[] b;
    private int locX;
    private final int locY;

    public Bar(int x, int y) {
        this.locX = x;
        this.locY = y;
        b = new BufferedImage[2];
        try {
            b[0] = ImageIO.read(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("sprites/down.png")));
            b[1] = ImageIO.read(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("sprites/up.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(b[0], locX, locY, null);
        g.drawImage(b[1], locX, locY + b[0].getHeight() + 125, null);

    }

    public void scroll() {

        locX -= 3;
    }

    public boolean offScreen() {
        return locX <= -b[0].getWidth();
    }

    public boolean hit(int x, int y, int width, int height) {

        return (x + width >= locX && x <= locX + b[0].getWidth()
                && ((y >= locY && y <= locY + b[0].getHeight()) || (y + height >= (locY + 125 + b[0].getHeight())
                        && y + height <= (locY + 125 + b[1].getHeight() + b[0].getHeight()))));
    }

    public boolean pass(int x, int y) {
        return (x >= locX && x < locX + b[0].getWidth() / 3 && y >= locY + b[0].getHeight()
                && y <= locY + 125 + b[0].getHeight());

    }

}
