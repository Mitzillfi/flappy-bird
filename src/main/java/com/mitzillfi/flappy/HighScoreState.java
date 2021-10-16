
package com.mitzillfi.flappy;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class HighScoreState extends GameState {
    private GameBG bg;
    BufferedImage gameOver;
    BufferedImage submit;
    private Floor floor;
    private Flappy f;
    private HighScore highScore;
    private boolean buttonclick = false;
    private BufferedImage leaderboardImage;
    String s = "";
    int width = 0;
    int i;
    boolean b = false;
    private Rectangle usInput;
    private Rectangle sub;
    private Rectangle leaderboardbutton;

    public HighScoreState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            gameOver = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/gameover.png"));
            submit = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/submit.png"));
            leaderboardImage = ImageIO
                    .read(this.getClass().getClassLoader().getResourceAsStream("sprites/leaderboard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bg = new GameBG("night");
        floor = new Floor();

        highScore = new HighScore(gsm.getHighScore(), true);

        highScore.setXY(50, 75);

        f = new Flappy();
        f.setLocY(Screen.HEIGHT / 2);
        usInput = new Rectangle((Screen.WIDTH / 2 - 80) - 10, (int) ((Screen.HEIGHT * .40) - 10), 120, 50);
        sub = new Rectangle(Screen.WIDTH / 2 - (submit.getWidth()) - 20, (int) (Screen.HEIGHT * .6),
                submit.getWidth() * 2, submit.getHeight() * 2);
        leaderboardbutton = new Rectangle(Screen.WIDTH / 2 - (submit.getWidth() - 120), (int) (Screen.HEIGHT * .6),
                (int) (submit.getWidth() * .50) * 2, submit.getHeight() * 2);
    }

    public void update() {
        bg.scroll();
        floor.scroll();
        f.animate();
    }

    public void draw(Graphics2D g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        width = g.getFontMetrics().stringWidth(s);
        bg.draw(g);
        floor.draw(g);
        g.drawImage(gameOver, Screen.WIDTH / 2 - (gameOver.getWidth() / 2), (int) (Screen.HEIGHT * .05), null);
        g.drawImage(submit, Screen.WIDTH / 2 - (submit.getWidth()) - 20, (int) (Screen.HEIGHT * .6),
                submit.getWidth() * 2, submit.getHeight() * 2, null);
        g.drawImage(leaderboardImage, Screen.WIDTH / 2 - (submit.getWidth() - 120), (int) (Screen.HEIGHT * .6),
                (int) (submit.getWidth() * .50) * 2, submit.getHeight() * 2, null);
        f.draw(g);
        highScore.draw(g);
        g.drawRect(Screen.WIDTH / 2 - 80, (int) (Screen.HEIGHT * .40), 155, 30);

        g.drawString(s, Screen.WIDTH / 2 - 75, (int) (Screen.HEIGHT * .44));
        if (width < 140 && s.length() < 14 && buttonclick) {
            g.drawString(blink(), Screen.WIDTH / 2 - 75 + width, (int) (Screen.HEIGHT * .44));
        }

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {

            if (leaderboardbutton.contains(e.getX(), e.getY())) {
                System.out.println("getting leadrboard");
                gsm.setCurrentState(GameStateManager.LEADERBOADSTATE);
            } else if (usInput.contains(e.getX(), e.getY())) {
                System.out.println("user input clicked");
                buttonclick = true;

            } else if (sub.contains(e.getX(), e.getY())) {
                if (s.length() > 0) {

                    try (Connection connection = gsm.getConnection();
                            PreparedStatement pStatement = connection
                                    .prepareStatement("INSERT INTO leaderboard (name, score)" + "Values(?,?)");) {
                        pStatement.setString(1, s);
                        pStatement.setInt(2, gsm.getHighScore());
                        pStatement.executeUpdate();
                        sub = new Rectangle(-1, -1, 0, 0);
                    } catch (SQLException | URISyntaxException ev) {
                        ev.printStackTrace();
                    }

                } else {
                    System.out.println("need input");
                }
            } else {
                gsm.setCurrentState(GameStateManager.PLAYSTATE);
            }
        }

    }

    public void keyPressed(KeyEvent e) {

        if (buttonclick && s.length() < 14 && width < 140) {
            if ((e.getKeyChar() + "").matches("^[a-zA-Z0-9 ]*$")) {
                s += e.getKeyChar();
            }
            System.out.println(e.getKeyChar());

        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && s.length() > 0) {
            s = s.substring(0, s.length() - 1);
        }

    }

    public String blink() {
        i++;
        if (i % 50 == 0)
            b = !b;
        return b ? "" : "|";
    }

}
