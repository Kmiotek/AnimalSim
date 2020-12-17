package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;
import agh.cs.animalsim.IMapElement;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;

public class NumberChart extends Chart {

    public NumberChart(TropicSimulationEngine engine, String title, double[] initialDataX, double[] initialDataY) {
        super(engine, title, new String[] {"Herbivores", "Carnivores"},new double[][]{new double[]{0}, new double[]{0}},
                new double[][] {initialDataX, initialDataY});
    }

    @Override
    public void died(IMapElement object) {
        if (object.isCarnivore()){
            deleteFrom(1);
        } else {
            deleteFrom(0);
        }
        updateChart();
    }

    @Override
    public void wasBorn(IMapElement object) {
        object.registerDeathObserver(this);
        if (object.isCarnivore()){
            addTo(1);
        } else {
            addTo(0);
        }
        updateChart();
    }

    private void deleteFrom(int i){
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


}
