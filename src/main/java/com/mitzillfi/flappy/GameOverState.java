
package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverState extends GameState {
    private GameBG bg;
    BufferedImage gameOver;
    private Score score;
    private Floor floor;
    private Flappy f;
    private HighScore highScore;
    private Rectangle leaderboardbutton;
    private BufferedImage leaderboardImage;

    public GameOverState(GameStateManager gameStateManager) {

        this.gsm = gameStateManager;
        try {
            leaderboardImage = ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/leaderboard.png"));
            gameOver = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bg = new GameBG("night");
        floor = new Floor();
        score = gsm.getScore();
        highScore = new HighScore(gsm.getHighScore());
        score.setXY(50, 50);
        highScore.setXY(50, 150);
        score.setDisplayScore(true);
        f = new Flappy();
        f.setLocY(Screen.HEIGHT / 2);
        leaderboardbutton = new Rectangle(Screen.WIDTH / 2 - (leaderboardImage.getWidth() + 20),
                (int) (Screen.HEIGHT * .58), (int) (leaderboardImage.getWidth() * .7) * 2,
                leaderboardImage.getHeight() * 2);
    }

    public void update() {
        bg.scroll();
        floor.scroll();
        f.animate();

    }

    public void draw(Graphics2D g) {
        bg.draw(g);
        floor.draw(g);
        g.drawImage(gameOver, Screen.WIDTH / 2 - (gameOver.getWidth() / 2), (int) (Screen.HEIGHT * .05), null);
        g.drawImage(leaderboardImage, Screen.WIDTH / 2 - (leaderboardImage.getWidth() + 20),
                (int) (Screen.HEIGHT * .58), (int) (leaderboardImage.getWidth() * .7) * 2,
                leaderboardImage.getHeight() * 2, null);
        score.draw(g);
        f.draw(g);
        highScore.draw(g);

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {

            if (leaderboardbutton.contains(e.getX(), e.getY())) {
                System.out.println("getting leadrboard");
                gsm.setCurrentState(GameStateManager.LEADERBOADSTATE);
            } else {
                gsm.setCurrentState(1);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gsm.setCurrentState(1);
        }
    }
}
