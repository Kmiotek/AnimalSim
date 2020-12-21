package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChartPanel extends JPanel implements ActionListener {
    Chart numberChart;
    Chart emptyChart;

    HighlightPanel highlightPanel;

    JButton chart1;
    JButton chart2;
    JButton chart3;

    JPanel charts;
    JPanel buttons;

    int current = 1;

    public ChartPanel(int numberOfHerbivores, int numberOfCarnivores, TropicSimulationEngine engine){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numberChart = new QuantityChart(engine, "Herbivores and carnivores", new double[]{numberOfHerbivores},
                new double[]{numberOfCarnivores}, new double[]{0});
        emptyChart = new SizeSpeedChart(engine, "Average speed and size", new double[][]{new double[]{10}, new double[]{10}},
                numberOfHerbivores+numberOfCarnivores);
        highlightPanel = new HighlightPanel(engine);

        buttons = new ButtonPanel();
        chart1 = new JButton("Quantities");
        chart1.setActionCommand("quantities");
        chart1.addActionListener(this);
        buttons.add(chart1);

        chart2 = new JButton("Traits");
        chart2.setActionCommand("traits");
        chart2.addActionListener(this);
        buttons.add(chart2);

        chart3 = new JButton("Highlighted");
        chart3.setActionCommand("highlighted");
        chart3.addActionListener(this);
        chart3.setVisible(false);
        buttons.add(chart3);

        buttons.setBackground(Color.GRAY);

        charts = new JPanel();
        charts.add(numberChart);
        charts.add(emptyChart);
        emptyChart.setVisible(false);
        charts.add(highlightPanel);
        highlightPanel.setVisible(false);
        add(buttons);
        add(charts);

    }

    public ArrayList<ILifeObserver> getChartsAsObservers(){
        ArrayList<ILifeObserver> ret = new ArrayList<>();
        ret.add(numberChart);
        ret.add(emptyChart);
        ret.add(highlightPanel);
        return ret;
    }

    public void setHighlightPanelVisible(){
        chart3.setVisible(true);
        highlightPanel.update();
    }

    public void setHighlightPanelInvisible(){
        chart3.setVisible(false);
        highlightPanel.update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("quantities".equals(e.getActionCommand())){
            if (current != 1){
                current = 1;
                for (Component comp : charts.getComponents()){
                    comp.setVisible(false);
                }
                numberChart.setVisible(true);
            }
        } else if ("traits".equals(e.getActionCommand())){
            if (current != 2){
                current = 2;
                for (Component comp : charts.getComponents()){
                    comp.setVisible(false);
                }
                emptyChart.setVisible(true);
            }
        } else if ("highlighted".equals(e.getActionCommand())) {
            if (current != 3) {
                current = 3;
                for (Component comp : charts.getComponents()){
                    comp.setVisible(false);
                }
                highlightPanel.setVisible(true);
                highlightPanel.update();
            }
        }
    }
}
