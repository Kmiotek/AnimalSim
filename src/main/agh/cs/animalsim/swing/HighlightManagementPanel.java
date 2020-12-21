package agh.cs.animalsim.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighlightManagementPanel extends JPanel implements ActionListener {

    protected JSpinner ticksSpinner;
    protected TropicSimulationEngine engine;

    JButton startStop;

    public HighlightManagementPanel(TropicSimulationEngine engine){
        ticksSpinner = addLabeledSpinner("How long do you want to run with this animal highlighted?",
                new SpinnerNumberModel(500, 0, 1000000, 10),
                "#");

        startStop = new JButton("Run simulation");
        startStop.setActionCommand("start");
        startStop.addActionListener(this);
        startStop.setFont(new Font("Arial", Font.PLAIN, 20));
        add(startStop);

        this.engine = engine;


        setBackground(Color.BLUE);
    }

    protected JSpinner addLabeledSpinner(String labelString, SpinnerModel model, String decimalFormatPattern) {
        JLabel label = new JLabel(labelString);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(label);

        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Arial", Font.PLAIN, 18));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, decimalFormatPattern));
        label.setLabelFor(spinner);
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
