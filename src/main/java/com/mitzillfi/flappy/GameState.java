package com.mitzillfi.flappy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {
    protected GameStateManager gsm;

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void mouseClicked(MouseEvent e);

    public abstract void keyPressed(KeyEvent e);
}
