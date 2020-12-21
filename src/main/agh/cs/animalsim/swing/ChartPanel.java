package agh.cs.animalsim.swing;

import agh.cs.animalsim.ILifeObserver;
import agh.cs.animalsim.StatisticManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChartPanel extends JPanel implements ActionListener {
    Chart numberChart;
    Chart traitsChart;
    Chart lifespanChart;
    Chart childrenChart;

    HighlightStatisticsPanel highlightStatisticsPanel;

    JPanel charts;
    JPanel buttons;

    JButton highlightButton;

    int current = 1;

    public ChartPanel(TropicSimulationEngine engine, StatisticManager statisticManager){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        numberChart = new Chart(engine, statisticManager, "Number of herbivores and carnivores",
                new String[]{"Herbivores", "Carnivores"}, new String[]{"herbivores", "carnivores"},
                new boolean[]{false, false}, new Color[]{Color.BLUE, Color.RED, Color.GREEN}, 600, 725);
        traitsChart = new Chart(engine, statisticManager, "Average speed and size",
                new String[]{"Size", "Speed"}, new String[]{"size", "speed"},
                new boolean[]{false, false}, new Color[]{Color.RED, Color.BLUE}, 600, 725);
        lifespanChart = new Chart(engine, statisticManager, "Average lifespan of dead animals",
                new String[]{"Lifespan"}, new String[]{"lifespan"},
                new boolean[]{false}, new Color[]{Color.BLACK}, 600, 725);
        childrenChart = new Chart(engine, statisticManager, "Average number of children",
                new String[]{"Herbivores", "Carnivores"}, new String[]{"children_herbivores", "children_carnivores"},
                new boolean[]{false}, new Color[]{Color.BLUE, Color.RED}, 600, 725);
        highlightStatisticsPanel = new HighlightStatisticsPanel(engine);

        buttons = new JPanel();

        addButton("Quantities", "quantities");
        addButton("Traits", "traits");
        addButton("Lifespan", "lifespan");
        addButton("Children", "children");

        highlightButton = addButton("Highlighted", "highlighted");
        highlightButton.setVisible(false);

        buttons.setBackground(Color.GRAY);

        charts = new JPanel();
        charts.add(numberChart);
        charts.add(traitsChart);
        charts.add(lifespanChart);
        charts.add(childrenChart);
        for (Component comp : charts.getComponents()){
            comp.setVisible(false);
        }
        numberChart.setVisible(true);
        charts.add(highlightStatisticsPanel);
        highlightStatisticsPanel.setVisible(false);
        add(buttons);
        add(charts);

    }

    private JButton addButton(String text, String command){
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setActionCommand(command);
        buttons.add(button);
        return button;
    }

    public void setHighlightPanelButtonVisible(){
        highlightButton.setVisible(true);
        highlightStatisticsPanel.update();
    }

    public void setHighlightPanelButtonInvisible(){
        highlightButton.setVisible(false);
        highlightStatisticsPanel.update();
    }

    public void update(){
        numberChart.updateChart();
        traitsChart.updateChart();
        lifespanChart.updateChart();
        childrenChart.updateChart();
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
                traitsChart.setVisible(true);
            }
        } else if ("highlighted".equals(e.getActionCommand())) {
            if (current != 3) {
                current = 3;
                for (Component comp : charts.getComponents()){
                    comp.setVisible(false);
                }
                highlightStatisticsPanel.setVisible(true);
                highlightStatisticsPanel.update();
            }
        } else if ("lifespan".equals(e.getActionCommand())){
            if (current != 4){
                current = 4;
                for (Component comp : charts.getComponents()){
                    comp.setVisible(false);
                }
                lifespanChart.setVisible(true);
            }
        } else if ("children".equals(e.getActionCommand())){
            if (current != 5){
                current = 5;
                for (Component comp : charts.getComponents()){
                    comp.setVisible(false);
                }
                childrenChart.setVisible(true);
            }
        }
    }
}
