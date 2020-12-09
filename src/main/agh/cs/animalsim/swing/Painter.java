package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Painter extends Canvas implements Runnable, ActionListener, ItemListener {
    protected IWorldMap map;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected TropicSimulation updater;

    protected final Color brown = new Color(69, 47, 15);
    protected final Color savannaColor = new Color(128, 123, 5);
    protected final Color jungleColor  = new Color(60, 100, 5);

    private boolean running = true;
    Thread animator;

    public Painter(IWorldMap map, int numberOfHerbivores, int numberOfCarnivores, int amountOfGrass){
        this.map = map;
        updater = new TropicSimulation(map);
        for (int i =0;i<numberOfHerbivores;i++){
            updater.createAnimal(false, 10, 10);
        }
        for (int i =0;i<numberOfCarnivores;i++){
            updater.createAnimal(true, 20, 11);
        }
        for (int i =0;i<amountOfGrass;i++){
            updater.createGrass();
        }
    }

    public void setLowerLeft(Vector2d lowerLeft){
        this.lowerLeft = lowerLeft;
    }

    public void setUpperRight(Vector2d upperRight){
        this.upperRight = upperRight;
    }

    public void paint(Graphics g){
        setBackground(Color.WHITE);
        g.setColor(savannaColor);
        upperRight = map.upperRightCorner();
        lowerLeft = map.lowerLeftCorner();
        g.fillRect(50, 50, upperRight.x - lowerLeft.x, upperRight.y - lowerLeft.y);
        Collection<Vector2d> syncCollection = Collections.synchronizedCollection(map.getObjectsPositions());
        for(Vector2d position : syncCollection){
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
            Vector2d newPos = object.getPosition().modulo(map.upperRightCorner());
            g.fillOval(newPos.x - size/2 - lowerLeft.x + 50,
                    newPos.y - size/2 - lowerLeft.y + 50, size, size);
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

            if (running) {
                updater.update();

                repaint();
            }

            timeDiff = System.currentTimeMillis() - beforeTime;
            int DELAY =60;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("stop".equals(e.getActionCommand())) {
            running = false;
        } else if ("start".equals(e.getActionCommand())) {
            running = true;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
