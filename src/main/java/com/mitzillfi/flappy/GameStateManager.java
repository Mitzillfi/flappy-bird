package com.mitzillfi.flappy;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameStateManager {
    public static final int MENUSTATE = 0;
    public static final int PLAYSTATE = 1;
    public static final int GAMEOVERSTATE = 2;
    public static final int HIGHSCORESTATE = 3;
    public static final int LEADERBOADSTATE = 4;
    private ArrayList<GameState> gameStates;
    private int currentState;
    private static Score score = new Score();
    private int highScore = 0;
    private Path folderPath;
    private Path filePath;
    private String uri;

    public GameStateManager() {
        uri = "postgres://cpjocbqjaefozk:e14223df72656f442252bf2211573e4a2942562f7f207fa27cafc5a1d686e57c@ec2-54-146-4-66.compute-1.amazonaws.com:5432/ddrticblf1q3lg";
        gameStates = new ArrayList<>();
        gameStates.add(null);
        gameStates.add(null);
        gameStates.add(null);
        gameStates.add(null);
        gameStates.add(null);

        init();
    }

    public void init() {

        String pathFile = null;
        if (System.getProperty("os.name").contains("Win")) {

            pathFile = System.getenv("AppData");
        } else {
            pathFile = System.getProperty("user.home");
        }

        folderPath = Paths.get(pathFile, ".config");
        filePath = folderPath.resolve("properties.cofn");
        try {
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                    try (FileOutputStream out = new FileOutputStream(filePath.toFile());
                            ObjectOutputStream obj = new ObjectOutputStream(out);) {
                        obj.writeInt(highScore);
                    }

                }
            }
            try (FileInputStream in = new FileInputStream(filePath.toFile());
                    ObjectInputStream obj = new ObjectInputStream(in)) {
                highScore = obj.readInt();
            }

        } catch (IOException | NumberFormatException e) {

            e.printStackTrace();
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setCurrentState(int state) {

        if (state == 1) {
            score = new Score();
            gameStates.set(state, new PlayState(this));
        } else if (state == 2) {

            gameStates.set(state, new GameOverState(this));
        } else if (state == 0) {

            gameStates.set(state, new MenuState(this));
        } else if (state == 3) {
            gameStates.set(state, new HighScoreState(this));
        } else if (state == 4) {
            gameStates.set(state, new LeaderBoardState(this));
        }

        currentState = state;

    }

    public void draw(Graphics2D g) {
        gameStates.get(currentState).draw(g);

    }

    public void update() {
        gameStates.get(currentState).update();

    }

    public void mouseClicked(MouseEvent e) {
        gameStates.get(currentState).mouseClicked(e);

    }

    public void keyPressed(KeyEvent e) {
        gameStates.get(currentState).keyPressed(e);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int s) {
        highScore = s;
    }

    public Score getScore() {
        return score;
    }

    public Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(uri);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
                + "?sslmode=require";

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
