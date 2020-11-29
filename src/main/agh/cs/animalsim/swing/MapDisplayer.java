package agh.cs.animalsim.swing;

import agh.cs.animalsim.IWorldMap;
import agh.cs.animalsim.Vector2d;

import javax.swing.*;
import java.awt.*;

public class MapDisplayer {
    private Painter painter;

    public MapDisplayer(IWorldMap map){
        painter = new Painter(map);
    }

    public void display(Vector2d lowerLeft, Vector2d upperRight){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Simulation");
        JMenuItem startMenuItem = new JMenuItem("Pause");
        menuBar.add(fileMenu);
        fileMenu.add(startMenuItem);

        painter.setLowerLeft(lowerLeft);
        painter.setUpperRight(upperRight);
        JFrame f=new JFrame("Evolution");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(painter);
        f.setSize(upperRight.getX()-lowerLeft.getX() + 130, upperRight.getY()-lowerLeft.getY() + 150 + 20);
        f.setLocationRelativeTo(null);
        f.setJMenuBar(menuBar);
        f.setVisible(true);
    }

}
