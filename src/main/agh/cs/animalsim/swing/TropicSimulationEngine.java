package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class TropicSimulationEngine implements Runnable, ActionListener, ChangeListener, IEngine, MouseListener {
    private final TropicPainter painter;
    private Chart chart;
    private JMenuBar menuBar;
    private IWorldMap map;
    private TropicSimulation updater;
    private JFrame frame;
    private HighlightManager manager;

    private boolean paused = true;
    private boolean running = true;
    private boolean timeBound = false;

    private int timer = 0;

    private int FPS = 30;
    private int generation = 0;

    IMapElement highlighted = null;

    public TropicSimulationEngine(TropicMap map, int numberOfHerbivores, int numberOfCarnivores, double grassPerTick,
                                  int initialSize, int initialSpeed, int initialEnergy, int meatQuality, int vision){
        painter = new TropicPainter(map);
        menuBar = new JMenuBar();
        chart = new NumberChart(this, "Herbivores and carnivores", new double[]{numberOfHerbivores}, new double[]{numberOfCarnivores});
        manager = new HighlightManager(this);

        JMenu startMenu = new JMenu("Simulation");
        startMenu.setFont(new Font("Arial", Font.PLAIN, 18));
        JMenu speedMenu = new JMenu("Speed");
        speedMenu.setFont(new Font("Arial", Font.PLAIN, 18));

        menuBar.add(startMenu);
        menuBar.add(speedMenu);

        JMenuItem startMenuItemStop = new JMenuItem("Pause");
        startMenuItemStop.setFont(new Font("Arial", Font.PLAIN, 18));
        JMenuItem startMenuItemGo = new JMenuItem("Run");
        startMenuItemGo.setFont(new Font("Arial", Font.PLAIN, 18));

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

        updater = new TropicSimulation(map, chart, grassPerTick);
        for (int i =0;i<numberOfHerbivores;i++){
            updater.createAnimal(false, initialSize, initialSpeed, initialEnergy, meatQuality, 10, 50, vision);
        }
        for (int i =0;i<numberOfCarnivores;i++){
            updater.createAnimal(true, initialSize, initialSpeed, initialEnergy, meatQuality, 10, 60, vision);
        }
    }

    @Override
    public void start(){
        frame =new JFrame("Evolution");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1600,
                900);

        frame.add(painter, BorderLayout.CENTER);
        frame.add(chart, BorderLayout.LINE_START);

        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
                                    @Override
                                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                        super.windowClosing(windowEvent);
                                        running = false;
                                    }
                                });

        painter.addMouseListener(this);

        Thread animator = new Thread(this);
        animator.start();
    }

    private void disableManager(){
        frame.remove(manager);
        frame.invalidate();
        frame.validate();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (running) {

            if (!paused) {
                if (timeBound){
                    timer--;
                    if (timer < 0 || highlighted.isDead()){
                        paused = true;
                        timeBound = false;
                        highlighted.setHighlighted(false);
                        highlighted = null;
                        disableManager();
                    }
                }
                generation++;
                updater.update();
            }
            painter.repaint();

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
            if (highlighted != null) {
                highlighted.setHighlighted(false);
                highlighted = null;
                disableManager();
            }
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            FPS = source.getValue();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (timeBound){
            return;
        }
        IMapElement h = map.objectClosestTo(painter.getMapPosition(new Vector2d(e.getX(), e.getY())));
        if (highlighted != null) {
            highlighted.setHighlighted(false);
        }
        highlighted = h;
        if (highlighted != null) {
            highlighted.setHighlighted(true);
            if (paused) {
                frame.add(manager, BorderLayout.PAGE_END);
                frame.invalidate();
                frame.validate();
            }
        } else {
            disableManager();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void startHighlightedSimulation(int ticks){
        timeBound = true;
        timer = ticks;
        paused = false;
    }

    public int getGeneration(){
        return generation;
    }
}
