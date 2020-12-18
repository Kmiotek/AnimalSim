package agh.cs.animalsim.swing;

import agh.cs.animalsim.IMapElement;

public class SizeSpeedChart extends Chart{
    int numberOfAnimals;

    public SizeSpeedChart(TropicSimulationEngine engine, String title, double[][] initialDataY,
                          int initialNumberOfAnimals) {
        super(engine, title, new String[]{"Size", "Speed"}, new double[][]{new double[]{0}, new double[]{0}}, initialDataY);
        numberOfAnimals = initialNumberOfAnimals;
    }

    @Override
    public void died(IMapElement object) {
        double currentSpeedSum = axisY.get(1).get(axisY.get(1).size()-1) * numberOfAnimals;
        currentSpeedSum -= object.getSpeed();

        double currentSizeSum = axisY.get(0).get(axisY.get(0).size()-1) * numberOfAnimals;
        currentSizeSum -= object.getSize();
        numberOfAnimals--;
        axisX.get(1).add((double) engine.getGeneration());
        axisY.get(1).add(currentSpeedSum/numberOfAnimals);

        axisX.get(0).add((double) engine.getGeneration());
        axisY.get(0).add(currentSizeSum/numberOfAnimals);
        updateChart();
    }

    @Override
    public void wasBorn(IMapElement object) {
        object.registerDeathObserver(this);
        double currentSpeedSum = axisY.get(1).get(axisY.get(1).size()-1) * numberOfAnimals;
        currentSpeedSum += object.getSpeed();

        double currentSizeSum = axisY.get(0).get(axisY.get(0).size()-1) * numberOfAnimals;
        currentSizeSum += object.getSize();
        numberOfAnimals++;
        axisX.get(1).add((double) engine.getGeneration());
        axisY.get(1).add(currentSpeedSum/numberOfAnimals);

        axisX.get(0).add((double) engine.getGeneration());
        axisY.get(0).add(currentSizeSum/numberOfAnimals);
        updateChart();
    }
}
