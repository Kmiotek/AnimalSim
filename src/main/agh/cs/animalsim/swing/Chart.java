package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Chart extends JPanel implements ILifeObserver{
    protected XYChart chart;
    protected TropicSimulationEngine engine;

    protected ArrayList<String> seriesNames;
    protected ArrayList< ArrayList<Double> > seriesAxesX;
    protected ArrayList< ArrayList<Double> > seriesAxesY;

    XChartPanel<XYChart> chartPane;

    String title;

    public Chart(TropicSimulationEngine engine, String title, String[] seriesNames, double[][] initialDataX, double[][] initialDataY,
                 int width, int height) {
        seriesAxesX = new ArrayList<>();
        seriesAxesY = new ArrayList<>();
        this.seriesNames = new ArrayList<>();
        this.title = title;
        for (int series = 0; series < seriesNames.length; series++) {
            ArrayList<Double> listX = new ArrayList<>();
            ArrayList<Double> listY = new ArrayList<>();
            for (int i =0;i<initialDataX[series].length;i++){
                listX.add(initialDataX[series][i]);
                listY.add(initialDataY[series][i]);
            }
            seriesAxesX.add(listX);
            seriesAxesY.add(listY);
            this.seriesNames.add(seriesNames[series]);
        }

        this.engine = engine;

        chart = new XYChartBuilder().width(width).height(height).title(title).build();
        addToChart();
        chartPane = new XChartPanel<>(chart);
        add(chartPane);

    }

    public void enableSeries(String seriesName){
        chart.getSeriesMap().get(seriesName).setEnabled(true);
    }

    public void disableSeries(String seriesName){
        chart.getSeriesMap().get(seriesName).setEnabled(false);
    }


    protected void addToChart(){
        for (int i = 0; i < seriesNames.size(); i++) {
            chart.addSeries(seriesNames.get(i), toPrimitive(seriesAxesX.get(i).toArray(new Double[0])),
                    toPrimitive(seriesAxesY.get(i).toArray(new Double[0])));
        }
    }

    protected void updateChart() {      // this is baaad
        for (int i = 0; i < seriesNames.size(); i++) {
            chart.updateXYSeries(seriesNames.get(i), toPrimitive(seriesAxesX.get(i).toArray(new Double[0])),
                    toPrimitive(seriesAxesY.get(i).toArray(new Double[0])), null);
        }
        chartPane.revalidate();
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
