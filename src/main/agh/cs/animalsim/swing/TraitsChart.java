package agh.cs.animalsim.swing;

import agh.cs.animalsim.Animal;
import agh.cs.animalsim.IMapElement;

public class TraitsChart extends Chart{
    int numberOfAnimals;

    public TraitsChart(TropicSimulationEngine engine, String title, double[][] initialDataY,
                       int initialNumberOfAnimals) {
        super(engine, title, new String[]{"Size", "Speed"}, new double[][]{new double[]{0}, new double[]{0}}, initialDataY, 600, 725);
        numberOfAnimals = initialNumberOfAnimals;
    }

    @Override
    public void died(IMapElement object) {
        if (! (object instanceof Animal)){
            return;
        }
        addNextValues((Animal)object, -1);
        updateChart();
    }

    @Override
    public void wasBorn(IMapElement object) {
        if (! (object instanceof Animal)){
            return;
        }
        object.registerLifeObserver(this);

        addNextValues((Animal)object, 1);
        updateChart();
    }

    private void addNextValues(Animal object, int increase){
        double currentSpeedSum = seriesAxesY.get(1).get(seriesAxesY.get(1).size()-1) * numberOfAnimals;
        currentSpeedSum += object.getSpeed() * increase;

        double currentSizeSum = seriesAxesY.get(0).get(seriesAxesY.get(0).size()-1) * numberOfAnimals;
        currentSizeSum += object.getSize() * increase;

        numberOfAnimals += increase;

        seriesAxesX.get(1).add((double) engine.getGeneration());
        seriesAxesY.get(1).add(currentSpeedSum/numberOfAnimals);

        seriesAxesX.get(0).add((double) engine.getGeneration());
        seriesAxesY.get(0).add(currentSizeSum/numberOfAnimals);
    }
}
