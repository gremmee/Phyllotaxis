package nl.gremmee.phyllotaxis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Cell {
    private Double a;
    private ID id;
    private int x;
    private int y;
    private Random random = new Random();

    public Cell(Double aA, int aX, int aY, ID aId) {
        a = aA;
        x = aX;
        y = aY;
    }

    public void doRender(Graphics g) {
        float h = Utils.map((a.intValue() % 256), 0, 255, 0, 1);
        float s = Utils.map(255, 0, 255, 0, 1);
        float b = Utils.map(255, 0, 255, 0, 1);
        g.setColor(Color.getHSBColor(h, s, b));
        g.fillOval(x, y, 8, 8);
    }

    public void doUpdate() {
    }

    public ID getID() {
        return this.id;
    }

    public Random getRandom() {
        return this.random;
    }

    public void render(Graphics aGraphics) {
        doRender(aGraphics);
    }

    public void setId(ID aId) {
        this.id = aId;
    }

    public void update() {
        doUpdate();
    }
}
