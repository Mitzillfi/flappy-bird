package com.mitzillfi.flappy;

import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class App extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public App() {
        setTitle("Flappy Bird");
        try {
            setIconImage(ImageIO
                    .read(Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("sprites/ico.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setResizable(false);
        Screen screen = new Screen();
        add(screen);
        pack();
        screen.start();

        System.out.println(getSize());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new App();
    }
}
