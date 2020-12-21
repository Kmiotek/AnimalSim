package agh.cs.animalsim.swing;

import agh.cs.animalsim.IMapElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class QuantityChart extends Chart implements ItemListener {

    JCheckBox grassEnabled;

    public QuantityChart(TropicSimulationEngine engine, String title, double[] initialDataHerbivores,
                         double[] initialDataCarnivores, double[] initialDataGrass) {
        super(engine, title, new String[] {"Herbivores", "Carnivores", "Grass"},new double[][]{new double[]{0}, new double[]{0}, new double[]{0}},
                new double[][] {initialDataHerbivores, initialDataCarnivores, initialDataGrass}, 600, 700);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        grassEnabled = new JCheckBox("Show grass");
        grassEnabled.addItemListener(this);
        grassEnabled.setSelected(true);
        add(grassEnabled);
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
        object.registerLifeObserver(this);
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
        if (seriesAxesX.get(i).get(seriesAxesX.get(i).size() - 1) == engine.getGeneration()) {
            seriesAxesY.get(i).set(seriesAxesX.get(i).size() - 1, seriesAxesY.get(i).get(seriesAxesX.get(i).size() - 1) - 1);
        } else {
            seriesAxesX.get(i).add((double) engine.getGeneration());
            seriesAxesY.get(i).add(seriesAxesY.get(i).get(seriesAxesY.get(i).size() - 1) - 1);
        }
    }

    private void addTo(int i){
        if (seriesAxesX.get(i).get(seriesAxesX.get(i).size() - 1) == engine.getGeneration()) {
            seriesAxesY.get(i).set(seriesAxesX.get(i).size() - 1, seriesAxesY.get(i).get(seriesAxesX.get(i).size() - 1) + 1);
        } else {
            seriesAxesX.get(i).add((double) engine.getGeneration());
            seriesAxesY.get(i).add(seriesAxesY.get(i).get(seriesAxesY.get(i).size() - 1) + 1);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if (source == grassEnabled) {
            if (e.getStateChange() == ItemEvent.DESELECTED){
                disableSeries("Grass");
            } else {
                enableSeries("Grass");
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
