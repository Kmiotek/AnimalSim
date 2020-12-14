package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TropicPainter extends JPanel implements Runnable, ActionListener, ItemListener, ChangeListener {
    protected IWorldMap map;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected TropicSimulation updater;

    protected final Color brown = new Color(69, 47, 15);
    protected final Color savannaColor = new Color(128, 123, 5);
    protected final Color jungleColor  = new Color(60, 100, 5);

    private boolean running = false;
    private int FPS = 30;


    public TropicPainter(IWorldMap map, int numberOfHerbivores, int numberOfCarnivores, int amountOfGrass){
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

    @Override
    public void paintComponent(Graphics g){
        TropicMap map1 = (TropicMap) map;
        setBackground(Color.WHITE);
        upperRight = map.upperRightCorner();
        lowerLeft = map.lowerLeftCorner();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, upperRight.x - lowerLeft.x + 100, upperRight.y - lowerLeft.y + 100);
        g.setColor(savannaColor);
        g.fillRect(50, 50, upperRight.x - lowerLeft.x, upperRight.y - lowerLeft.y);
        g.setColor(jungleColor);
        g.fillRect(map1.junglePos().x + 50, map1.junglePos().y + 50, map1.jungleSize.x, map1.jungleSize.y);
        for(Drawable object : map.getDrawableObjects()){
            g.setColor(object.color);
            int size = (int)(Math.sqrt(object.size *20));
            Vector2d newPos = object.position.modulo(map.upperRightCorner());
            g.fillOval(newPos.x - size/2 - lowerLeft.x + 50,
                    newPos.y - size/2 - lowerLeft.y + 50, size, size);
        }
        //g.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        Thread animator = new Thread(this);
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
            int DELAY = 1000/FPS;
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

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            FPS = source.getValue();
        }
    }
}
