package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;
import agh.cs.animalsim.IMapElement;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collector;

public abstract class Chart extends JPanel implements ILifeObserver{
    protected XYChart chart;
    protected TropicSimulationEngine engine;

    protected ArrayList<String> seriesNames;
    protected ArrayList< ArrayList<Double> > axisX;
    protected ArrayList< ArrayList<Double> > axisY;


    public Chart(TropicSimulationEngine engine, String title, String[] seriesNames, double[][] initialDataX, double[][] initialDataY) {
        axisX = new ArrayList<>();
        axisY = new ArrayList<>();
        this.seriesNames = new ArrayList<>();
        chart = new XYChartBuilder().width(600).title(title).build();

        for (int s = 0;s<seriesNames.length; s++) {
            chart.addSeries(seriesNames[s], initialDataX[s], initialDataY[s]);
            ArrayList<Double> listX = new ArrayList<>();
            ArrayList<Double> listY = new ArrayList<>();
            for (int i =0;i<initialDataX[s].length;i++){
                listX.add(initialDataX[s][i]);
                listY.add(initialDataY[s][i]);
            }
            axisX.add(listX);
            axisY.add(listY);
            this.seriesNames.add(seriesNames[s]);
        }

        this.engine = engine;


        //chart.updateXYSeries()
        //chart.updateXYSeries("Herbivores", new double[] { 0.0, 1.0, 19.0, 10 }, new double[] { 0.0, 2.0, 15.0, 18 }, null);

        setLayout(new BorderLayout());

        XChartPanel<XYChart> chartPane = new XChartPanel<>(chart);
        add(chartPane);

    }

    protected void updateChart() {
        for (int i = 0; i < seriesNames.size(); i++) {
            chart.updateXYSeries(seriesNames.get(i), toPrimitive(axisX.get(i).toArray(new Double[0])),
                    toPrimitive(axisY.get(i).toArray(new Double[0])), null);
        }
        repaint();
    }

    private double[] toPrimitive(Double[] array){
        double[] result = new double[array.length];
        for (int i =0;i<array.length;i++){
            result[i] = array[i];
        }
        return result;
    }
}
