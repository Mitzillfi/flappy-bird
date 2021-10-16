package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Flappy {
    private static final double GRAVITY = -9.8;
    private BufferedImage yellowbird;
    private int locX = Screen.WIDTH / 2 - 50;
    private int locY = Screen.HEIGHT / 2 - 100;
    private double v = 0;
    private ArrayList<BufferedImage> animation;
    private int frameTrack = 0;

    public Flappy() {
        animation = new ArrayList<>();
        try {
            yellowbird = ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/yellowbird-downflap.png"));
            animation.add(yellowbird);
            animation.add(ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/yellowbird-midflap.png")));
            animation.add(ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/yellowbird-upflap.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g) {
        g.drawImage(yellowbird, locX, locY, yellowbird.getWidth(), yellowbird.getHeight(), null);
    }

    public void animate() {
        yellowbird = animation.get((frameTrack / 7));
        frameTrack++;
        if (frameTrack >= 21)
            frameTrack = 0;
    }

    public void jump() {
        v = -175;
        try {
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("audio/wing.wav")));
            c.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }

    public void hit() {
        try {
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("audio/hit.wav")));
            c.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void gotPoint() {
        try {
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("audio/point.wav")));
            c.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public void fall() {
        v += -GRAVITY * 5 / 4;

        if (v >= 14) {
            v = 10;
            translate(0, (int) v);
        } else {
            translate(0, (int) (v / 15));
        }

    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getHeight() {
        return yellowbird.getHeight();
    }

    public int getWidth() {
        return yellowbird.getWidth();
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public void translate(int x, int y) {
        this.locX += x;
        this.locY += y;
    }
}
