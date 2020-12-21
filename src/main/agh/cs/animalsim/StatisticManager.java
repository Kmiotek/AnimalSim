package agh.cs.animalsim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticManager implements ILifeObserver{

    private Double numberOfHerbivores;
    private Double numberOfCarnivores;
    private Double averageSpeed;
    private Double averageSize;
    private Double averageLifeSpanOfDead;
    private int deadAnimals;
    private Double averageNumberOfChildrenOfHerbivores;
    private Double averageNumberOfChildrenOfCarnivores;


    private final Map<String, Double> map;

    public StatisticManager(int initialNumberOfHerbivores, int initialNumberOfCarnivores, double initialAverageSize,
                            double initialAverageSpeed){
        numberOfHerbivores = (double) initialNumberOfHerbivores;
        numberOfCarnivores = (double) initialNumberOfCarnivores;
        averageSize = initialAverageSize;
        averageSpeed = initialAverageSpeed;
        averageNumberOfChildrenOfHerbivores = 0.0;
        averageNumberOfChildrenOfCarnivores = 0.0;
        deadAnimals = 0;
        averageLifeSpanOfDead = 0.0;
        map = new HashMap<>();
        map.put("herbivores", numberOfHerbivores);
        map.put("carnivores", numberOfCarnivores);
        map.put("speed", averageSpeed);
        map.put("size", averageSize);
        map.put("lifespan", averageLifeSpanOfDead);
        map.put("children_herbivores", averageNumberOfChildrenOfHerbivores);
        map.put("children_carnivores", averageNumberOfChildrenOfCarnivores);
    }

    public double getCurrentValueOf(String statistic){
        return map.get(statistic);
    }

    public double getNumberOfAnimals(){
        return numberOfCarnivores+numberOfHerbivores;
    }

    @Override
    public void died(IMapElement object) {
        if (object instanceof Animal) {
            Animal animal = (Animal) object;
            updateEverything(animal, true);
        }
    }

    @Override
    public void wasBorn(IMapElement object) {
        if (object instanceof Animal) {
            Animal animal = (Animal) object;
            updateEverything(animal, false);
        }
        object.registerLifeObserver(this);
    }

    private void updateEverything(Animal animal, boolean deleting){
        int increase = 1;
        if (deleting){
            increase = -1;
        }

        double currentSpeedSum = averageSpeed * getNumberOfAnimals();
        double currentSizeSum = averageSize * getNumberOfAnimals();
        double currentLifeSpanSum = averageLifeSpanOfDead * deadAnimals;
        double currentNumberOfChildrenOfHerbivoresSum = averageNumberOfChildrenOfHerbivores * numberOfHerbivores;
        double currentNumberOfChildrenOfCarnivoresSum = averageNumberOfChildrenOfCarnivores * numberOfCarnivores;

        currentSpeedSum += animal.getSpeed() * increase;
        currentSizeSum += animal.getSize() * increase;

        if (!deleting){
            if (animal.getParentsType()){
                currentNumberOfChildrenOfCarnivoresSum+=2;
            } else {
                currentNumberOfChildrenOfHerbivoresSum+=2;
            }
        } else {
            currentLifeSpanSum += animal.getAge();
            deadAnimals++;
            averageLifeSpanOfDead = currentLifeSpanSum/deadAnimals;
            if (animal.isCarnivore()) {
                currentNumberOfChildrenOfCarnivoresSum -= animal.getChildren().size();
            } else {
                currentNumberOfChildrenOfHerbivoresSum -= animal.getChildren().size();
            }
        }

        if (animal.isCarnivore()){
            numberOfCarnivores += increase;
        } else {
            numberOfHerbivores += increase;
        }

        averageSpeed = currentSpeedSum/getNumberOfAnimals();
        averageSize = currentSizeSum/getNumberOfAnimals();

        if (numberOfHerbivores != 0) {
            averageNumberOfChildrenOfHerbivores = currentNumberOfChildrenOfHerbivoresSum / numberOfHerbivores;
        } else {
            averageNumberOfChildrenOfHerbivores = 0.0;
        }
        if (numberOfCarnivores != 0) {
            averageNumberOfChildrenOfCarnivores = currentNumberOfChildrenOfCarnivoresSum / numberOfCarnivores;
        } else {
            averageNumberOfChildrenOfCarnivores = 0.0;
        }

        map.replace("herbivores", numberOfHerbivores);
        map.replace("carnivores", numberOfCarnivores);
        map.replace("speed", averageSpeed);
        map.replace("size", averageSize);
        map.replace("lifespan", averageLifeSpanOfDead);
        map.replace("children_herbivores", averageNumberOfChildrenOfHerbivores/2);
        map.replace("children_carnivores", averageNumberOfChildrenOfCarnivores/2);
    }

    @Override
    public String toString(){
        String result = "";
        for (String statistic : map.keySet()) {
            result = result.concat(statistic).concat(map.get(statistic).toString());
        }
        return result;
    }

    public void saveToFile(String fileName){
        String path;
        String directoryPath = new File("").getAbsolutePath().concat("\\data\\");
        path = directoryPath.concat(fileName);
        try {
            File myObj = new File(path);
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred while creating a file");
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(toString());
        } catch (IOException e){
            System.out.println("An error occurred while writing to file");
            e.printStackTrace();
        }
    }
}
