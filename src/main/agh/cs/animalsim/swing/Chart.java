package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;
import agh.cs.animalsim.StatisticManager;
import agh.cs.animalsim.Vector2f;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.MatlabTheme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chart extends JPanel {
    protected XYChart chart;
    protected StatisticManager statisticManager;
    private TropicSimulationEngine engine;

    protected Map< String, Data > data;

    private XChartPanel<XYChart> chartPane;

    public Chart(TropicSimulationEngine engine, StatisticManager statisticManager, String title, String[] seriesNames,
                 String[] seriesCommands, boolean[] disableable, Color[] colors, int width, int height) {
        data = new HashMap<>();
        chart = new XYChartBuilder().width(width).height(height).title(title).build();
        for (int i =0;i<seriesNames.length;i++){
            Data series = new Data();
            series.dataX.add((double) engine.getGeneration());
            series.dataY.add(statisticManager.getCurrentValueOf(seriesCommands[i]));
            series.updateCommand = seriesCommands[i];
            data.put(seriesNames[i], series);
            chart.addSeries(seriesNames[i], series.dataX, series.dataY);
        }
        this.statisticManager = statisticManager;
        this.engine = engine;

        chart.getStyler().setSeriesColors(colors);
        chartPane = new XChartPanel<>(chart);
        add(chartPane);
    }

    public void enableSeries(String seriesName){
        chart.getSeriesMap().get(seriesName).setEnabled(true);
    }

    public void disableSeries(String seriesName){
        chart.getSeriesMap().get(seriesName).setEnabled(false);
    }


    protected void updateChart() {
        for (String series : data.keySet()){
            Data seriesData = data.get(series);
            seriesData.dataX.add((double) engine.getGeneration());
            seriesData.dataY.add(statisticManager.getCurrentValueOf(seriesData.updateCommand));
            chart.updateXYSeries(series, toPrimitive(seriesData.dataX.toArray(new Double[0])),
                    toPrimitive(seriesData.dataY.toArray(new Double[0])), null);
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
