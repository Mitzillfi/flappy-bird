package com.mitzillfi.flappy;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {

    private static final long serialVersionUID = 3070576465806295621L;

    public static final int WIDTH = 288;
    public static final int HEIGHT = 512;
    private static boolean running = false;
    private transient BufferedImage image;
    private transient Graphics2D g;
    private transient Thread thread;
    private transient GameStateManager gsm;
    private transient MouseListener mListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            gsm.mouseClicked(e);

        }
    };

    private transient KeyListener kListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            gsm.keyPressed(e);
        }
    };

    public Screen() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addMouseListener(mListener);
        addKeyListener(kListener);
    }

    private void init() {

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();
        running = true;
        gsm = new GameStateManager();
        gsm.setCurrentState(0);
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, "game");
            thread.start();
        }
    }

    public void run() {
        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();

                delta--;
            }
            draw();
            drawToScreen();

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }

        }

    }

    public void update() {
        gsm.update();
    }

    public void draw() {
        paintComponent(g);
        gsm.draw(g);

    }

    public void drawToScreen() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

}
