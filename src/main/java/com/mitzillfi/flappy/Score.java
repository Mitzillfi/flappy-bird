package com.mitzillfi.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Score {
    private BufferedImage[] scores;
    private int currentScore = 0;
    private char[] sHelper;
    private int wHelper = 0;
    private int y = 0;
    private int x = 0;
    private BufferedImage scoreImage;
    private boolean displayScoretext = false;

    public Score() {
        scores = new BufferedImage[10];
        try {
            scoreImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/score.png"));
            for (int i = 0; i < scores.length; i++) {

                scores[i] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g) {
        if (displayScoretext) {
            g.drawImage(scoreImage, (Screen.WIDTH / 2 - 30) + (wHelper) + x - 105, 35 + y,
                    scoreImage.getWidth() * 8 / 3, scoreImage.getHeight() * 8 / 3 + 1, null);
        }
        sHelper = (currentScore + "").toCharArray();
        for (int i = 0; i < sHelper.length; i++) {
            g.drawImage(scores[sHelper[i] - '0'], (Screen.WIDTH / 2 - 30) + (wHelper) + 17 + x, 50 + y,
                    scores[sHelper[i] - '0'].getWidth() + 5, scores[sHelper[i] - '0'].getHeight() + 5, null);
            wHelper += scores[sHelper[i] - '0'].getWidth() + 1;
        }

        wHelper = 0;

    }

    public void addScore() {
        this.currentScore += 1;

    }

    public void setXY(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public void setDisplayScore(boolean b) {
        displayScoretext = b;
    }

    public int getCurrentScore() {
        return currentScore;
    }

}
