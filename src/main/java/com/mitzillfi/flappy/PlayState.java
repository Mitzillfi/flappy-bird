package com.mitzillfi.flappy;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlayState extends GameState {
    private Floor floor;
    private Flappy flappy;
    private GameBG gameBG;
    protected Score score;
    private Obstacle bar;

    public PlayState(GameStateManager gsm) {
        this.gsm = gsm;
        flappy = new Flappy();
        floor = new Floor();
        gameBG = new GameBG("day");
        score = gsm.getScore();
        bar = new Obstacle();
    }

    public void update() {

        if (!hit()) {

            getScore();
            gameBG.scroll();
            floor.scroll();
            flappy.fall();
            flappy.animate();
            bar.scroll();
            bar.destroy();
            bar.spawn();
        }
        if (hit()) {
            // score.getCurrentScore()
            flappy.hit();
            if (score.getCurrentScore() > gsm.getHighScore()) {
                gsm.setHighScore(score.getCurrentScore());
                try (FileOutputStream out = new FileOutputStream(gsm.getFilePath().toFile());
                        ObjectOutputStream obj = new ObjectOutputStream(out);) {
                    obj.writeInt(score.getCurrentScore());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gsm.setCurrentState(GameStateManager.HIGHSCORESTATE);
            } else {

                gsm.setCurrentState(GameStateManager.GAMEOVERSTATE);
            }
        }
    }

    public boolean hit() {

        return (bar.hit(flappy.getLocX(), flappy.getLocY(), flappy.getWidth(), flappy.getHeight()));

    }

    public void getScore() {

        if (bar.pass(flappy.getLocX(), flappy.getLocY())) {
            score.addScore();
            flappy.gotPoint();
        }

    }

    public void draw(Graphics2D g) {
        gameBG.draw(g);
        bar.draw(g);
        score.draw(g);
        floor.draw(g);
        flappy.draw(g);

    }

    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == 1) {
            flappy.jump();

        }
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            flappy.jump();

        }
    }

}
