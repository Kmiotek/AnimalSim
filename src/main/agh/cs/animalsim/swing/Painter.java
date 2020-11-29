package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Painter extends Canvas implements Runnable {
    private IWorldMap map;
    private Vector2d lowerLeft;
    private Vector2d upperRight;

    private final Color brown = new Color(69, 47, 15);
    private final Color savanna = new Color(128, 123, 5);

    private final int DELAY = 50;
    private Thread animator;

    public Painter(IWorldMap map){
        this.map = map;
    }

    public void setLowerLeft(Vector2d lowerLeft){
        this.lowerLeft = lowerLeft;
    }

    public void setUpperRight(Vector2d upperRight){
        this.upperRight = upperRight;
    }

    public void paint(Graphics g){
        setBackground(Color.WHITE);
        g.setColor(savanna);
        upperRight = map.upperRightCorner();
        lowerLeft = map.lowerLeftCorner();
        g.fillRect(50, 50, upperRight.getX() - lowerLeft.getX(), upperRight.getY() - lowerLeft.getY());
        for(Vector2d position : map.getObjectsPositions()){
            IMapElement object = map.objectAt(position);
            if (object instanceof Animal){
                if(object.isCarnivore()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(brown);
                }
            } else if (object instanceof Grass){
                g.setColor(Color.GREEN);
            }
            int size = object.getDrawingSize();
            Vector2d newPos = object.getPosition().dualMod(map.upperRightCorner());
            g.fillOval(newPos.getX() - size/2 - lowerLeft.getX() + 50,
                    newPos.getY() - size/2 - lowerLeft.getY() + 50, size, size);
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            ArrayList<IMapElement> elements = new ArrayList<>();

            for (Vector2d element : map.getObjectsPositions()) {
                elements.add(map.objectAt(element));
            }
            for (IMapElement element : elements) {
                element.go();
            }
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}
