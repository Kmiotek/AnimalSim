package agh.cs.animalsim.swing;

import agh.cs.animalsim.IWorldMap;
import agh.cs.animalsim.TropicMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class TropicSimulationEngine  {
    private final TropicPainter painter;
    private JMenuBar menuBar;
    private IWorldMap map;

    public TropicSimulationEngine(TropicMap map, int numberOfHerbivores, int numberOfCarnivores, int amountOfGrass){
        painter = new TropicPainter(map, numberOfHerbivores, numberOfCarnivores, amountOfGrass);
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
        framesPerSecond.addChangeListener(painter);

        speedMenu.add(framesPerSecond);
        startMenu.add(startMenuItemStop);
        startMenuItemStop.setActionCommand("stop");
        startMenuItemStop.addActionListener(painter);
        startMenu.add(startMenuItemGo);
        startMenuItemGo.setActionCommand("start");
        startMenuItemGo.addActionListener(painter);
        this.map = map;
    }

    public void start(){
        painter.setLowerLeft(map.lowerLeftCorner());
        painter.setUpperRight(map.upperRightCorner());
        JFrame f=new JFrame("Evolution");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setSize(map.upperRightCorner().x-map.lowerLeftCorner().x + 130,
                map.upperRightCorner().y-map.lowerLeftCorner().y + 150 + 20);
        f.add(painter);

        f.setJMenuBar(menuBar);
        f.setLocationRelativeTo(null);

        f.setVisible(true);
    }



}
