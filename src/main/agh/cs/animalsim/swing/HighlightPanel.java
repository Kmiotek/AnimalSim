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

public class HighlightPanel extends JPanel implements ILifeObserver {

    TropicSimulationEngine engine;
    XChartPanel<PieChart> chartPane;
    PieChart chart;

    JPanel status;
    JLabel statusL;
    JPanel diedIn;
    JLabel diedInL;
    JPanel numberOfChildren;
    JLabel numberOfChildrenL;
    JPanel numberOfDescendants;
    JLabel numberOfDescendantsL;
    JPanel numberOfLivingDescendants;
    JLabel numberOfLivingDescendantsL;

    public HighlightPanel(TropicSimulationEngine engine){
        this.engine = engine;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        statusL = new JLabel("Alive");
        status = addText("Status:", statusL);
        diedInL = new JLabel("");
        diedIn = addText("Died in", diedInL);
        diedIn.setVisible(false);
        numberOfChildrenL = new JLabel("");
        numberOfChildren = addText("Number of children", numberOfChildrenL);
        numberOfDescendantsL = new JLabel("");
        numberOfDescendants = addText("Number of descendants", numberOfDescendantsL);
        numberOfLivingDescendantsL = new JLabel("");
        numberOfLivingDescendants = addText("Living descendants", numberOfLivingDescendantsL);

        chart = new PieChartBuilder().width(600).height(400).title("Descendant type").build();

        // Customize Chart
        Color[] sliceColors = new Color[] { Color.RED, Color.CYAN};
        chart.getStyler().setSeriesColors(sliceColors);

        // Series
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
                statusL.setText("Alive");
                diedIn.setVisible(false);
            }
            numberOfChildrenL.setText(String.valueOf(engine.getHighlighted().getChildren().size()));
            numberOfDescendantsL.setText(String.valueOf(descendants.size()));
            numberOfLivingDescendantsL.setText(String.valueOf(numberOfLivingAnimals(descendants)));
            chart.updatePieSeries("Carnivores", numberOfCarnivores((descendants)));
            chart.updatePieSeries("Herbivores", numberOfHerbivores((descendants)));
            repaint();
        }

    }

    private JPanel addText(String label, JLabel value){
        value.setForeground(Color.BLACK);
        value.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel label2 = new JLabel(label);
        label2.setForeground(Color.BLACK);
        label2.setFont(new Font("Arial", Font.PLAIN, 20));
        JPanel panel = new JPanel();
        panel.add(label2);
        panel.add(value);
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
            statusL.setText("Dead");
            diedIn.setVisible(true);
            diedInL.setText(String.valueOf(engine.getGeneration()));
        }
        update();
    }

    @Override
    public void wasBorn(IMapElement object) {
        object.registerDeathObserver(this);
        update();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 725);
    }
}
