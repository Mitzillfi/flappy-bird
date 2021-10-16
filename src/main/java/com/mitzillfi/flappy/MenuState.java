package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuState extends GameState {
    private BufferedImage bg;
    private BufferedImage msg;
    private int anm = 1;
    private int fps = 0;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void update() {

        if (fps >= 30) {
            anm = 1 - anm;
            fps = 0;
        }
        fps++;
        try {
            msg = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/message" + anm + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        try {
            bg = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/background-day.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(bg, 0, 0, Screen.WIDTH, Screen.HEIGHT, null);
        g.drawImage(msg, 0, 0, Screen.WIDTH, Screen.HEIGHT, null);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {

            gsm.setCurrentState(GameStateManager.PLAYSTATE);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gsm.setCurrentState(GameStateManager.PLAYSTATE);
        }
    }

}
