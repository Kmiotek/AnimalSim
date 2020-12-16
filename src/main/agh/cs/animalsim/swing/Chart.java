package agh.cs.animalsim.swing;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;

public class Chart extends JPanel {
    private XYChart chart;

    public Chart() {
        chart = new XYChartBuilder().width(400).height(200).title("I want to die").build();

        setLayout(new BorderLayout());

        XChartPanel<XYChart> chartPane = new XChartPanel<>(chart);
        add(chartPane);

    }
}
