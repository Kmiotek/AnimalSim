package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class TropicSimulationEngine implements Runnable, ActionListener, ChangeListener, IEngine{
    private final TropicPainter painter;
    private JMenuBar menuBar;
    private IWorldMap map;
    private TropicSimulation updater;
    private JFrame f;

    private boolean paused = true;
    private boolean running = true;

    private int FPS = 30;

    public TropicSimulationEngine(TropicMap map, int numberOfHerbivores, int numberOfCarnivores, double grassPerTick,
                                  int initialSize, int initialSpeed, int initialEnergy, int meatQuality, int vision){
        painter = new TropicPainter(map);
        menuBar = new JMenuBar();

        JMenu startMenu = new JMenu("Simulation");
        JMenu speedMenu = new JMenu("Speed");

        menuBar.add(startMenu);
        menuBar.add(speedMenu);

        JMenuItem startMenuItemStop = new JMenuItem("Pause");
        JMenuItem startMenuItemGo = new JMenuItem("Run");

        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 5, 200, 30);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(5, new JLabel("5") );
        labelTable.put(30, new JLabel("30") );
        labelTable.put(60, new JLabel("60") );
        labelTable.put(120, new JLabel("120") );
        labelTable.put(200, new JLabel("200") );
        framesPerSecond.setLabelTable( labelTable );
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.addChangeListener(this);

        speedMenu.add(framesPerSecond);
        startMenu.add(startMenuItemStop);
        startMenuItemStop.setActionCommand("stop");
        startMenuItemStop.addActionListener(this);
        startMenu.add(startMenuItemGo);
        startMenuItemGo.setActionCommand("start");
        startMenuItemGo.addActionListener(this);
        this.map = map;

        updater = new TropicSimulation(map, grassPerTick);
        for (int i =0;i<numberOfHerbivores;i++){
            updater.createAnimal(false, initialSize, initialSpeed, initialEnergy, meatQuality, 10, 50, vision);
        }
        for (int i =0;i<numberOfCarnivores;i++){
            updater.createAnimal(true, initialSize, initialSpeed, initialEnergy, meatQuality, 10, 60, vision);
        }
    }

    @Override
    public void start(){
        f=new JFrame("Evolution");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setSize(1600,
                900);
        painter.setResolution(new Vector2d(1600, 900));
        f.add(painter);

        f.setJMenuBar(menuBar);
        f.setLocationRelativeTo(null);

        f.setVisible(true);

        f.addWindowListener(new java.awt.event.WindowAdapter() {
                                    @Override
                                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                        super.windowClosing(windowEvent);
                                        running = false;
                                    }
                                });

        Thread animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (running) {

            painter.setResolution(new Vector2d(f.getBounds().width, f.getBounds().height));     // this is really bad, but i dont have patience or time for doing this right

            if (!paused) {
                updater.update();
                painter.repaint();
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

                JOptionPane.showMessageDialog(painter, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("stop".equals(e.getActionCommand())) {
            paused = true;
        } else if ("start".equals(e.getActionCommand())) {
            paused = false;
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            FPS = source.getValue();
        }
    }


}
