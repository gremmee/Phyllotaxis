package nl.gremmee.phyllotaxis;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class Phyllotaxis extends Canvas implements Runnable {
    public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int NUM_STARS = WIDTH;
    public static int speed = 1;
    public static int cols;
    public static int rows;
    public static Handler handler;
    public static int w = 30;
    public Double a = 0.0;
    public Double c = 10.0;
    public Double n = 0.0;
    public Double r = 0.0;
    public Double x = 0.0;
    public Double y = 0.0;
    private boolean running = false;
    private int frames = 0;
    private Thread thread;

    public Phyllotaxis() {
        handler = new Handler();

        new Window(WIDTH, HEIGHT, "Phyllotaxis", this);
    }

    public static void main(String[] aArgs) {
        new Phyllotaxis();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
                int stars = handler.getStars();
                System.out.println("W x H : " + WIDTH + " x " + HEIGHT + " FPS: " + frames + " : Cells " + stars);
                frames = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // g.setColor(new Color(0, 0, 0, 15));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);

        a = n * 137.3;
        r = c * Math.sqrt(n);

        x = (r * Math.cos(Math.toRadians(a))) + (WIDTH / 2);
        y = (r * Math.sin(Math.toRadians(a))) + (HEIGHT / 2);

        n++;

        if ((y < 0) || (y > HEIGHT)) {
            stop();
        }
        handler.addObject(new Cell(n, x.intValue(), y.intValue(), ID.Cell));

        g.dispose();
        bs.show();
    }

    private void update() {
        handler.update();
    }
}
