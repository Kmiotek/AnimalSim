package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Chart extends JPanel implements ILifeObserver{
    protected XYChart chart;
    protected TropicSimulationEngine engine;

    protected ArrayList<String> seriesNames;
    protected ArrayList< ArrayList<Double> > axisX;
    protected ArrayList< ArrayList<Double> > axisY;
    private ArrayList<XYSeries> series;
    protected ArrayList<Boolean> active;

    XChartPanel<XYChart> chartPane;

    String title;
    int height;

    public Chart(TropicSimulationEngine engine, String title, String[] seriesNames, double[][] initialDataX, double[][] initialDataY,
                 int width, int height) {
        axisX = new ArrayList<>();
        axisY = new ArrayList<>();
        this.seriesNames = new ArrayList<>();
        this.title = title;
        this.height = height;
        active = new ArrayList<>();
        series = new ArrayList<>();
        for (int s = 0;s<seriesNames.length; s++) {
            ArrayList<Double> listX = new ArrayList<>();
            ArrayList<Double> listY = new ArrayList<>();
            for (int i =0;i<initialDataX[s].length;i++){
                listX.add(initialDataX[s][i]);
                listY.add(initialDataY[s][i]);
            }
            axisX.add(listX);
            axisY.add(listY);
            this.seriesNames.add(seriesNames[s]);
            active.add(true);
        }

        this.engine = engine;

        chart = new XYChartBuilder().width(width).height(height).title(title).build();
        addToChart();
        chartPane = new XChartPanel<>(chart);
        add(chartPane);

    }


    protected void addToChart(){
        for (int i = 0; i < seriesNames.size(); i++) {
            series.add(chart.addSeries(seriesNames.get(i), toPrimitive(axisX.get(i).toArray(new Double[0])),
                    toPrimitive(axisY.get(i).toArray(new Double[0]))));
        }
    }

    protected void updateChart() {
        for (int i = 0; i < seriesNames.size(); i++) {
            chart.updateXYSeries(seriesNames.get(i), toPrimitive(axisX.get(i).toArray(new Double[0])),
                    toPrimitive(axisY.get(i).toArray(new Double[0])), null);
            series.get(i).setEnabled(active.get(i));
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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, height);
    }
}
