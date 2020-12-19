package agh.cs.animalsim.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighlightManager extends JPanel implements ActionListener {

    protected JSpinner ticksSpinner;
    protected TropicSimulationEngine engine;

    JButton startStop;

    public HighlightManager(TropicSimulationEngine engine){
        SpinnerModel model =
                new SpinnerNumberModel(500, 0, 1000000, 10);
        ticksSpinner = addLabeledSpinner("How long do you want to run with this animal highlighted?", model);
        ticksSpinner.setEditor(new JSpinner.NumberEditor(ticksSpinner, "#"));

        startStop = new JButton("Run simulation");
        startStop.setActionCommand("start");
        startStop.addActionListener(this);
        startStop.setFont(new Font("Arial", Font.PLAIN, 20));
        add(startStop);

        this.engine = engine;


        setBackground(Color.BLUE);
    }

    protected JSpinner addLabeledSpinner(String label, SpinnerModel model) {
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(l);

        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Arial", Font.PLAIN, 18));
        l.setLabelFor(spinner);
        this.add(spinner);

        return spinner;
    }

    public void actionPerformed(ActionEvent event) {
        if ("start".equals(event.getActionCommand())) {
            startStop.setActionCommand("stop");
            startStop.setText("Stop highlight");
            try {
                ticksSpinner.commitEdit();
            } catch (java.text.ParseException e) {
                System.out.println("Couldn't update value of spinner");
                System.out.println(e.getLocalizedMessage());
            }
            engine.startHighlightedSimulation((Integer) ticksSpinner.getValue());
        } else if ("stop".equals(event.getActionCommand())) {
            startStop.setActionCommand("start");
            startStop.setText("Run simulation");
            engine.stopHighlightedSimulation();
        }
    }
}
