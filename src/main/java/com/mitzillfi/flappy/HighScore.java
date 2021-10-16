package com.mitzillfi.flappy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HighScore {
    private BufferedImage[] scores;
    private int highScoreCount;
    private char[] sHelper;
    private int wHelper = 0;
    private int y = 0;
    private int x = 0;
    private BufferedImage scoreImage;
    private boolean b;

    public HighScore(int score) {
        scores = new BufferedImage[10];
        try {
            scoreImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/highscore.png"));
            for (int i = 0; i < scores.length; i++) {

                scores[i] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.highScoreCount = score;

    }

    public HighScore(int score, boolean b) {
        this(score);
        this.b = b;
        try {
            if (b) {
                scoreImage = ImageIO
                        .read(this.getClass().getClassLoader().getResourceAsStream("sprites/newRecord.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        if (b) {
            g.drawImage(scoreImage, (Screen.WIDTH / 2 - 30) + (wHelper) - 120 + x, 0 + y, scoreImage.getWidth() * 2,
                    scoreImage.getHeight() * 2, null);
        } else {
            g.drawImage(scoreImage, (Screen.WIDTH / 2 - 30) + (wHelper) - 125 + x, 0 + y, scoreImage.getWidth() * 8 / 3,
                    scoreImage.getHeight() * 8 / 3, null);
        }
        sHelper = (highScoreCount + "").toCharArray();
        for (int i = 0; i < sHelper.length; i++) {
            g.drawImage(scores[sHelper[i] - '0'], (Screen.WIDTH / 2 - 30) + (wHelper) - 55 + x, 60 + y,
                    scores[sHelper[i] - '0'].getWidth() + 5, scores[sHelper[i] - '0'].getHeight() + 5, null);
            wHelper += scores[sHelper[i] - '0'].getWidth() + 1;
        }

        wHelper = 0;

    }

    public void setXY(int x, int y) {
        this.y = y;
        this.x = x;
    }

}
