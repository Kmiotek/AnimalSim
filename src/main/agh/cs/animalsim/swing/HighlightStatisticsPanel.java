package agh.cs.animalsim.swing;

import agh.cs.animalsim.Animal;
import agh.cs.animalsim.ILifeObserver;
import agh.cs.animalsim.IMapElement;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class HighlightStatisticsPanel extends JPanel implements ILifeObserver {

    TropicSimulationEngine engine;
    XChartPanel<PieChart> chartPane;
    PieChart chart;

    JLabel status;
    JPanel diedInPanel;
    JLabel diedIn;
    JLabel numberOfChildren;
    JLabel numberOfDescendants;
    JLabel numberOfLivingDescendants;

    public HighlightStatisticsPanel(TropicSimulationEngine engine){
        this.engine = engine;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        status = new JLabel("Alive");
        addText("Status:", status);
        diedIn = new JLabel("");
        diedInPanel = addText("Died in", diedIn);
        diedInPanel.setVisible(false);
        numberOfChildren = new JLabel("");
        addText("Number of children", numberOfChildren);
        numberOfDescendants = new JLabel("");
        addText("Number of descendants", numberOfDescendants);
        numberOfLivingDescendants = new JLabel("");
        addText("Living descendants", numberOfLivingDescendants);

        chart = new PieChartBuilder().width(600).height(400).title("Descendant type").build();

        Color[] sliceColors = new Color[] { Color.RED, Color.BLUE};
        chart.getStyler().setSeriesColors(sliceColors);

        chart.addSeries("Carnivores", 0);
        chart.addSeries("Herbivores", 0);

        chartPane = new XChartPanel<>(chart);

        add(chartPane);
    }

    public void update(){
        Set<Animal> descendants;
        if (engine.getHighlighted() != null){
            descendants = getAllDescendants();
            if (!engine.getHighlighted().isDead()){
                status.setText("Alive");
                diedInPanel.setVisible(false);
            } else {
                status.setText("Dead");
                diedInPanel.setVisible(true);
            }
            numberOfChildren.setText(String.valueOf(engine.getHighlighted().getChildren().size()));
            numberOfDescendants.setText(String.valueOf(descendants.size()));
            numberOfLivingDescendants.setText(String.valueOf(numberOfLivingAnimals(descendants)));
            chart.updatePieSeries("Carnivores", numberOfCarnivores((descendants)));
            chart.updatePieSeries("Herbivores", numberOfHerbivores((descendants)));
            repaint();
        }

    }

    private JPanel addText(String labelString, JLabel label){
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel descriptionLabel = new JLabel(labelString);
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        JPanel panel = new JPanel();
        panel.add(descriptionLabel);
        panel.add(label);
        add(panel);
        return panel;
    }

    private int numberOfCarnivores(Set<Animal> animals){
        int result = 0;
        for (Animal anime : animals){
            if (anime.isCarnivore()){
                result++;
            }
        }
        return result;
    }

    private int numberOfHerbivores(Set<Animal> animals){
        int result = 0;
        for (Animal anime : animals){
            if (!anime.isCarnivore()){
                result++;
            }
        }
        return result;
    }

    private int numberOfLivingAnimals(Set<Animal> animals){
        int result = 0;
        for (Animal anime : animals){
            if (!anime.isDead()){
                result++;
            }
        }
        return result;
    }

    private Set<Animal> getAllDescendants(){
        Set<Animal> result = new HashSet<>();
        Stack<Animal> stack = new Stack<>();
        stack.push(engine.getHighlighted());
        while (!stack.empty()){
            for (Animal anime : stack.pop().getChildren()){
                if (!result.contains(anime)){
                    result.add(anime);
                    stack.push(anime);
                }
            }
        }
        return result;
    }

    @Override
    public void died(IMapElement object) {
        if (object == engine.getHighlighted()){
            diedIn.setText(String.valueOf(engine.getGeneration()));
        }
        update();
    }

    @Override
    public void wasBorn(IMapElement object) {
        object.registerLifeObserver(this);
        update();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 750);
    }
}
