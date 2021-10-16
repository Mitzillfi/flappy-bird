package com.mitzillfi.flappy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class LeaderBoardState extends GameState {
    private GameBG bg;
    private Floor floor;
    private BufferedImage image;
    private BufferedImage uparr;
    private BufferedImage downarr;
    private BufferedImage play;
    private Graphics2D g2;
    private List<NameScore> nameScoreList;
    private Rectangle upArrRect;
    private Rectangle downArrRect;
    private Rectangle playRect;
    private int offset = 0;

    public LeaderBoardState(GameStateManager gsm) {
        this.gsm = gsm;
        bg = new GameBG("night");
        floor = new Floor();
        try {
            uparr = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/uparr.png"));
            downarr = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/downarr.png"));
            play = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("sprites/play.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Connection connection = gsm.getConnection(); Statement stmt = connection.createStatement()) {
            nameScoreList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("SELECT name,score FROM leaderboard ORDER BY score DESC");

            while (rs.next()) {

                String name = rs.getString("name");
                int score = rs.getInt("score");
                NameScore sc = new NameScore(name, score);
                nameScoreList.add(sc);
            }
        } catch (URISyntaxException | SQLException s) {
            s.printStackTrace();
        }
        upArrRect = new Rectangle(Screen.WIDTH - 45, 42, 30, 40);
        downArrRect = new Rectangle(Screen.WIDTH - 45, Screen.HEIGHT - 80 - 32, 30, 40);
        playRect = new Rectangle(Screen.WIDTH / 2 - play.getWidth(), Screen.HEIGHT - 60, play.getWidth() * 2,
                play.getHeight() * 2);
        createImage();
    }

    public void createImage() {

        image = new BufferedImage(Screen.WIDTH - 80, (nameScoreList.size() + 1) * 40, BufferedImage.TYPE_INT_ARGB);

        g2 = image.createGraphics();
        g2.setColor(Color.BLACK);

        g2.setStroke(new java.awt.BasicStroke(2.5F));
        g2.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 23));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.drawString("player", 10, 25);
        g2.drawString("score", (int) (image.getWidth() / 1.58), 25);
        g2.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 16));

        g2.drawLine(40, 40, image.getWidth(), 40);
        for (int i = 0; i < nameScoreList.size(); i++) {
            g2.drawLine(0, 40 + i * 40, image.getWidth(), 40 + i * 40);

            g2.drawString(nameScoreList.get(i).getName(), 5, 65 + i * 40);
            g2.drawString(nameScoreList.get(i).getScore() + "", (int) (image.getWidth() / 1.3), 65 + i * 40);

        }

        g2.drawLine(0, image.getHeight(), image.getWidth(), image.getHeight());
        g2.drawRect(0, 0, image.getWidth(), nameScoreList.size() * 40 + 40);

        g2.drawLine((int) (image.getWidth() / 1.65), 0, (int) (image.getWidth() / 1.65),
                nameScoreList.size() * 40 + 80);
        g2.dispose();

    }

    public void update() {
        //

    }

    public void draw(Graphics2D g) {
        BufferedImage img = image.getSubimage(0, offset, image.getWidth(),
                (image.getHeight() > 400) ? 400 : image.getHeight());
        bg.draw(g);
        floor.draw(g);
        g.setColor(new Color(252, 156, 28, 220));

        g.fillRoundRect(20, 20, Screen.WIDTH - 40, Screen.HEIGHT - 80, 40, 40);
        g.drawImage(img, 40, 40, img.getWidth(), img.getHeight(), null);
        g.drawImage(uparr, Screen.WIDTH - 45, 42, 30, 40, null);
        g.drawImage(downarr, Screen.WIDTH - 45, Screen.HEIGHT - 80 - 32, 30, 40, null);

        g.drawImage(play, Screen.WIDTH / 2 - play.getWidth(), Screen.HEIGHT - 60, play.getWidth() * 2,
                play.getHeight() * 2, null);

    }

    public void mouseClicked(MouseEvent e) {
        if (upArrRect.contains(e.getX(), e.getY())) {
            if (offset > 0)
                offset -= 40;
            else {
                System.out.println("all the way top ");
            }
        } else if (downArrRect.contains(e.getX(), e.getY())) {

            if (offset < (image.getHeight() - 400)) {
                offset += 40;
            } else {
                System.out.println("all the way bottom");
            }
        } else if (playRect.contains(e.getX(), e.getY())) {
            gsm.setCurrentState(GameStateManager.PLAYSTATE);
        }

    }

    public void keyPressed(KeyEvent e) {
        //
    }

}
