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
import java.util.Hashtable;

public class TropicSimulationEngine implements Runnable, ActionListener, ChangeListener, IEngine, MouseListener {
    private final TropicPainter painter;
    private ChartPanel chartPanel;
    private JMenuBar menuBar;
    private IWorldMap map;
    private SimulatedObjectsManager simulatedObjectsManager;
    private JFrame frame;
    private HighlightManagementPanel highlightManager;
    private StatisticManager statisticManager;

    private boolean paused = true;
    private boolean running = true;
    private int FPS = 30;

    private int generation = 0;

    private boolean runningWithHighlight = false;
    private Animal highlighted = null;
    private int highlightedTimer = 0;


    public TropicSimulationEngine(TropicMap map, int numberOfHerbivores, int numberOfCarnivores, double grassPerTick,
                                  int initialSize, int initialSpeed, int moveEfficiency, int initialEnergy, int meatQuality,
                                  int vision){
        painter = new TropicPainter(map);
        menuBar = new JMenuBar();
        statisticManager = new StatisticManager(numberOfHerbivores, numberOfCarnivores, initialSize, initialSpeed);

        highlightManager = new HighlightManagementPanel(this);
        chartPanel = new ChartPanel(this, statisticManager);

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
        JMenuItem startMenuItemWrite = new JMenuItem("Save state to file");
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
        startMenu.add(startMenuItemWrite);
        startMenuItemWrite.setActionCommand("write");
        startMenuItemWrite.addActionListener(this);
        this.map = map;

        simulatedObjectsManager = new SimulatedObjectsManager(map, statisticManager, grassPerTick);
        for (int i =0;i<numberOfHerbivores;i++){
            simulatedObjectsManager.createAnimal(false, initialSize, initialSpeed, initialEnergy, meatQuality, moveEfficiency, 50, vision);
        }
        for (int i =0;i<numberOfCarnivores;i++){
            simulatedObjectsManager.createAnimal(true, initialSize, initialSpeed, initialEnergy, meatQuality, moveEfficiency, 60, vision);
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
        frame.add(chartPanel, BorderLayout.LINE_START);
        frame.add(highlightManager, BorderLayout.PAGE_END);
        highlightManager.setVisible(false);

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

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (running) {

            if (!paused) {
                if (runningWithHighlight){
                    highlightedTimer--;
                    if (highlightedTimer < 0){
                        highlightManager.actionPerformed(new ActionEvent(this, 99, "stop"));
                    }
                }
                generation++;
                simulatedObjectsManager.update();
                if (generation % 10 == 0)
                    chartPanel.update();
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
            if (highlighted != null)
                enableHighlightManager();
        } else if ("start".equals(e.getActionCommand())) {
            paused = false;
            if (!runningWithHighlight)
                disableHighlightManager();
        } else if ("write".equals(e.getActionCommand())) {
            statisticManager.saveToFile("simulationData.txt");
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
        if (runningWithHighlight){
            return;
        }
        Animal h = map.animalClosestTo(painter.getMapPosition(new Vector2d(e.getX(), e.getY())));
        if (highlighted != null) {
            highlighted.setHighlighted(false);
        }
        highlighted = h;
        if (highlighted != null) {
            highlighted.setHighlighted(true);
            if (paused) {
                enableHighlightManager();
            }
            chartPanel.setHighlightPanelButtonVisible();
            painter.repaint();
        } else {
            disableHighlightManager();
            chartPanel.setHighlightPanelButtonInvisible();
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
        runningWithHighlight = true;
        highlightedTimer = ticks;
        paused = false;
    }

    public void stopHighlightedSimulation(){
        runningWithHighlight = false;
        paused = true;
    }

    private void disableHighlightManager(){
        highlightManager.setVisible(false);
    }

    private void enableHighlightManager(){
        highlightManager.setVisible(true);
    }

    public int getGeneration(){
        return generation;
    }

    public Animal getHighlighted(){
        return highlighted;
    }
}
