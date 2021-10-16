package com.mitzillfi.flappy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Obstacle {
    private List<Bar> bars;
    private int timer = 0;

    public Obstacle() {
        bars = new ArrayList<>();

    }

    public void draw(Graphics2D g) {

        for (Bar b : bars) {
            b.draw(g);

        }

    }

    public void scroll() {
        for (Bar b : bars) {
            b.scroll();
        }

    }

    public void destroy() {
        bars.removeIf(Bar::offScreen);

    }

    public boolean hit(int x, int y, int width, int height) {
        if (y <= -100 || y >= Screen.HEIGHT - 110) {

            return true;
        }
        if (timer % 5 == 0) {
            for (Bar b : bars) {
                if (b.hit(x, y, width, height)) {
                    return true;

                }
            }

        }
        return false;
    }

    public boolean pass(int x, int y) {
        if (timer % 5 == 0) {
            for (Bar b : bars) {
                if (b.pass(x, y)) {
                    return true;

                }
            }

        }
        return false;
    }

    public void spawn() {
        if (timer % 60 == 0) {
            bars.add(new Bar((Screen.WIDTH) + 100, ThreadLocalRandom.current().nextInt(6, 32) * -10));
        }
        timer++;
    }
}
