package agh.cs.animalsim.swing;

import agh.cs.animalsim.TropicMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPainter extends JPanel implements ActionListener {

    protected JButton start;
    protected JSpinner energySpinner;
    protected JSpinner sizeSpinner;
    protected JSpinner speedSpinner;
    protected JSpinner meatQualitySpinner;
    protected JSpinner visionSpinner;
    protected JSpinner jungleSizeSpinner;

    protected JSpinner numberOfAnimalsSpinner;
    protected JSpinner grassPerTickSpinner;

    protected JSpinner sizeXSpinner;
    protected JSpinner sizeYSpinner;

    public ClientPainter(){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        SpinnerModel model7 =
                new SpinnerNumberModel(1600, 0, 10000, 10);
        sizeXSpinner = addLabeledSpinner("Map Width", model7);
        sizeXSpinner.setEditor(new JSpinner.NumberEditor(sizeXSpinner, "#"));

        SpinnerModel model8 =
                new SpinnerNumberModel(1800, 0, 10000, 10);
        sizeYSpinner = addLabeledSpinner("Map Height", model8);
        sizeYSpinner.setEditor(new JSpinner.NumberEditor(sizeYSpinner, "#"));

        SpinnerModel model5 =
                new SpinnerNumberModel(10, 0, 1000, 1);
        numberOfAnimalsSpinner = addLabeledSpinner("Number of Herbivores", model5);
        numberOfAnimalsSpinner.setEditor(new JSpinner.NumberEditor(numberOfAnimalsSpinner, "#"));

        SpinnerModel model6 =
                new SpinnerNumberModel(1, 0, 100, 0.01);
        grassPerTickSpinner = addLabeledSpinner("Added Grass per Tick", model6);
        grassPerTickSpinner.setEditor(new JSpinner.NumberEditor(grassPerTickSpinner, "#.##"));

        SpinnerModel model =
                new SpinnerNumberModel(50000, 0, 1000000, 10);
        energySpinner = addLabeledSpinner("Initial Energy", model);
        energySpinner.setEditor(new JSpinner.NumberEditor(energySpinner, "#"));

        SpinnerModel model2 =
                new SpinnerNumberModel(10, 0, 1000, 1);
        sizeSpinner = addLabeledSpinner("Initial Size", model2);
        sizeSpinner.setEditor(new JSpinner.NumberEditor(sizeSpinner, "#"));

        SpinnerModel model3 =
                new SpinnerNumberModel(10, 0, 1000, 1);
        speedSpinner = addLabeledSpinner("Initial Speed", model3);
        speedSpinner.setEditor(new JSpinner.NumberEditor(speedSpinner, "#"));

        SpinnerModel model9 =
                new SpinnerNumberModel(50, 0, 10000, 1);
        visionSpinner = addLabeledSpinner("Vision", model9);
        visionSpinner.setEditor(new JSpinner.NumberEditor(visionSpinner, "#"));

        SpinnerModel model4 =
                new SpinnerNumberModel(2000, 0, 100000, 10);
        meatQualitySpinner = addLabeledSpinner("Meat Quality", model4);
        meatQualitySpinner.setEditor(new JSpinner.NumberEditor(meatQualitySpinner, "#"));

        SpinnerModel model10 =
                new SpinnerNumberModel(25, 0, 75, 1);
        jungleSizeSpinner = addLabeledSpinner("Jungle-step ratio [%]", model10);
        jungleSizeSpinner.setEditor(new JSpinner.NumberEditor(jungleSizeSpinner, "#"));


        start = new JButton("Start new simulation");
        start.setActionCommand("start");
        start.addActionListener(this);
        add(start);

    }

    protected JSpinner addLabeledSpinner(String label, SpinnerModel model) {
        JLabel l = new JLabel(label);
        this.add(l);

        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        this.add(spinner);

        return spinner;
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        if ("start".equals(event.getActionCommand())) {
            try {
                energySpinner.commitEdit();
                sizeSpinner.commitEdit();
                speedSpinner.commitEdit();
                meatQualitySpinner.commitEdit();
                numberOfAnimalsSpinner.commitEdit();
                grassPerTickSpinner.commitEdit();
                sizeXSpinner.commitEdit();
                sizeYSpinner.commitEdit();
                visionSpinner.commitEdit();
                jungleSizeSpinner.commitEdit();
            } catch (java.text.ParseException e) {
                System.out.println("Couldn't update value of spinner");
                System.out.println(e.getLocalizedMessage());
            }
            double ratio = Math.sqrt((Integer) jungleSizeSpinner.getValue())/10.0;
            int x = (Integer) sizeXSpinner.getValue();
            int y = (Integer) sizeYSpinner.getValue();
            TropicMap map = new TropicMap(x, y, (int) (x*ratio), (int) (y*ratio));
            TropicSimulationEngine engine = new TropicSimulationEngine(map, (Integer) numberOfAnimalsSpinner.getValue(),
                    0, (Double) grassPerTickSpinner.getValue(),
                    (Integer) sizeSpinner.getValue(), (Integer) speedSpinner.getValue(), (Integer) energySpinner.getValue(),
                    (Integer) meatQualitySpinner.getValue(), (Integer) visionSpinner.getValue());
            engine.start();
        }
    }

}
