package agh.cs.animalsim.swing;

import agh.cs.animalsim.JsonManager;
import agh.cs.animalsim.TropicMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPainter extends JPanel implements ActionListener {

    private JButton start;
    private JSpinner energySpinner;
    private JSpinner sizeSpinner;
    private JSpinner speedSpinner;
    private JSpinner meatQualitySpinner;
    private JSpinner visionSpinner;
    private JSpinner jungleSizeSpinner;
    private JSpinner moveEfficiencySpinner;
    private JSpinner numberOfAnimalsSpinner;
    private JSpinner grassPerTickSpinner;

    private JSpinner sizeXSpinner;
    private JSpinner sizeYSpinner;

    public ClientPainter(){

        JsonManager jsonReader = new JsonManager("data.json");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        sizeXSpinner = addLabeledSpinner("Map Width",
                new SpinnerNumberModel(jsonReader.getInt("width"), 0, 10000, 10),
                "#");

        sizeYSpinner = addLabeledSpinner("Map Height",
                new SpinnerNumberModel(jsonReader.getInt("height"), 0, 10000, 10),
                "#");

        numberOfAnimalsSpinner = addLabeledSpinner("Number of Herbivores",
                new SpinnerNumberModel(jsonReader.getInt("herbivores"), 0, 1000, 1),
                "#");

        grassPerTickSpinner = addLabeledSpinner("Added Grass per Tick",
                new SpinnerNumberModel(jsonReader.getDouble("grass_per_tick"), 0, 100, 0.01),
                "#.##");

        energySpinner = addLabeledSpinner("Initial Energy",
                new SpinnerNumberModel(jsonReader.getInt("energy"), 0, 1000000, 10),
                "#");

        sizeSpinner = addLabeledSpinner("Initial Size",
                new SpinnerNumberModel(jsonReader.getInt("size"), 0, 1000, 1),
                "#");

        speedSpinner = addLabeledSpinner("Initial Speed",
                new SpinnerNumberModel(jsonReader.getInt("speed"), 0, 1000, 1),
                "#");

        moveEfficiencySpinner = addLabeledSpinner("Move efficiency",
                new SpinnerNumberModel(jsonReader.getInt("efficiency"), 0, 1000, 1),
                "#");

        visionSpinner = addLabeledSpinner("Vision",
                new SpinnerNumberModel(jsonReader.getInt("vision"), 0, 10000, 1),
                "#");

        meatQualitySpinner = addLabeledSpinner("Meat Quality",
                new SpinnerNumberModel(jsonReader.getInt("meat_quality"), 0, 100000, 10),
                "#");

        jungleSizeSpinner = addLabeledSpinner("Jungle-step ratio [%]",
                new SpinnerNumberModel(jsonReader.getInt("jungle_ratio"), 0, 50, 1),
                "#");

        start = new JButton("Start new simulation");
        start.setActionCommand("start");
        start.addActionListener(this);
        add(start);

    }

    private JSpinner addLabeledSpinner(String labelString, SpinnerModel model, String decimalFormatPattern) {
        JLabel label = new JLabel(labelString);
        this.add(label);

        JSpinner spinner = new JSpinner(model);
        label.setLabelFor(spinner);
        spinner.setEditor(new JSpinner.NumberEditor(spinner, decimalFormatPattern));
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
                moveEfficiencySpinner.commitEdit();
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
                    (Integer) sizeSpinner.getValue(), (Integer) speedSpinner.getValue(),
                    (Integer) moveEfficiencySpinner.getValue(), (Integer) energySpinner.getValue(),
                    (Integer) meatQualitySpinner.getValue(), (Integer) visionSpinner.getValue());
            engine.start();
        }
    }

}
