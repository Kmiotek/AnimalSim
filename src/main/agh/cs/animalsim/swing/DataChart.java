package agh.cs.animalsim.swing;

import agh.cs.animalsim.IMapElement;

public class DataChart extends Chart{
    public DataChart(TropicSimulationEngine engine, String title, String[] seriesNames, double[][] initialDataX, double[][] initialDataY) {
        super(engine, title, seriesNames, initialDataX, initialDataY);
    }

    @Override
    public void died(IMapElement object) {

    }

    @Override
    public void wasBorn(IMapElement object) {

    }
}
