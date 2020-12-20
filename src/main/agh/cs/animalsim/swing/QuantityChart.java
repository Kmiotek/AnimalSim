package agh.cs.animalsim.swing;

import agh.cs.animalsim.IMapElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class QuantityChart extends Chart implements ItemListener {

    JCheckBox box;

    public QuantityChart(TropicSimulationEngine engine, String title, double[] initialDataHerbivores,
                         double[] initialDataCarnivores, double[] initialDataGrass) {
        super(engine, title, new String[] {"Herbivores", "Carnivores", "Grass"},new double[][]{new double[]{0}, new double[]{0}, new double[]{0}},
                new double[][] {initialDataHerbivores, initialDataCarnivores, initialDataGrass}, 600, 700);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        box = new JCheckBox("Show grass");
        box.addItemListener(this);
        box.setSelected(true);
        add(box);
    }

    @Override
    public void died(IMapElement object) {
        if (object.isGrassy()){
            deleteFrom(2);
        } else if (object.isCarnivore()){
            deleteFrom(1);
        } else {
            deleteFrom(0);
        }
        updateChart();
    }

    @Override
    public void wasBorn(IMapElement object) {
        object.registerDeathObserver(this);
        if (object.isGrassy()){
            addTo(2);
        } else if (object.isCarnivore()){
            addTo(1);
        } else {
            addTo(0);
        }
        updateChart();
    }

    private void deleteFrom(int i){             // this is bad but no time to fix this
        if (axisX.get(i).get(axisX.get(i).size() - 1) == engine.getGeneration()) {
            axisY.get(i).set(axisX.get(i).size() - 1, axisY.get(i).get(axisX.get(i).size() - 1) - 1);
        } else {
            axisX.get(i).add((double) engine.getGeneration());
            axisY.get(i).add(axisY.get(i).get(axisY.get(i).size() - 1) - 1);
        }
    }

    private void addTo(int i){
        if (axisX.get(i).get(axisX.get(i).size() - 1) == engine.getGeneration()) {
            axisY.get(i).set(axisX.get(i).size() - 1, axisY.get(i).get(axisX.get(i).size() - 1) + 1);
        } else {
            axisX.get(i).add((double) engine.getGeneration());
            axisY.get(i).add(axisY.get(i).get(axisY.get(i).size() - 1) + 1);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if (source == box) {
            if (e.getStateChange() == ItemEvent.DESELECTED){
                active.set(2, false);
            } else {
                active.set(2, true);
            }
            chart.resetFilter();
            updateChart();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 725);
    }
}
