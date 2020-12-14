package agh.cs.animalsim.swing;

import agh.cs.animalsim.TropicMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPainter extends JPanel implements ActionListener {

    protected JButton start;

    public ClientPainter(){
        start = new JButton("Start new simulation");
        start.setActionCommand("start");
        start.addActionListener(this);
        add(start);
    }



    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            TropicMap map = new TropicMap(1600, 900, 800, 450);
            TropicSimulationEngine engine = new TropicSimulationEngine(map, 10, 0, 50);
            engine.start();
        }
    }

}
