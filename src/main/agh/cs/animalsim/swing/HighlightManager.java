package agh.cs.animalsim.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighlightManager extends JPanel implements ActionListener {

    protected JSpinner ticksSpinner;
    protected TropicSimulationEngine engine;

    public HighlightManager(TropicSimulationEngine engine){
        SpinnerModel model =
                new SpinnerNumberModel(500, 0, 1000000, 10);
        ticksSpinner = addLabeledSpinner("How long do you want to run with this animal highlighted?", model);
        ticksSpinner.setEditor(new JSpinner.NumberEditor(ticksSpinner, "#"));

        JButton start3 = new JButton("Run simulation");
        start3.setActionCommand("startN");
        start3.addActionListener(this);
        start3.setFont(new Font("Arial", Font.PLAIN, 20));
        add(start3);

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
        if ("startN".equals(event.getActionCommand())) {
            try {
                ticksSpinner.commitEdit();
            } catch (java.text.ParseException e) {
                System.out.println("Couldn't update value of spinner");
                System.out.println(e.getLocalizedMessage());
            }
            engine.startHighlightedSimulation((Integer) ticksSpinner.getValue());
        }
    }
}
