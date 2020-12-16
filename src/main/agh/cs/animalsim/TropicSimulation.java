package agh.cs.animalsim;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TropicSimulation implements ILifeObserver {

    private ArrayList<IMapElement> elementsForUpdating;
    private ArrayList<IMapElement> elementsForDeleting;
    private ArrayList<IMapElement> elementsForAdding;

    private IWorldMap map;

    private VectorRandomizer randomizer;

    private double grassPerTick;

    public TropicSimulation(IWorldMap map, double grassPerTick){
        this.map = map;
        elementsForDeleting = new ArrayList<>();
        elementsForUpdating = new ArrayList<>();
        elementsForAdding = new ArrayList<>();
        randomizer = new VectorRandomizer(map);
        this.grassPerTick = grassPerTick;
    }

    public void createAnimal(boolean carnivore, int size, int speed, int initialEnergy, int meatQuality, float moveEfficiency, int chanceOfLooking){
        TropicAnimal squirrel = new TropicAnimal(map, randomizer.randomVectorOnMapSmart(), carnivore, speed, size,
                initialEnergy, meatQuality, moveEfficiency, chanceOfLooking);
        elementsForUpdating.add(squirrel);
        squirrel.registerDeathObserver(this);
        map.place(squirrel);
    }

    public void createGrass(int nutrients){
        Grass grass = new Grass(map, map.upperRightCorner().subtract(map.lowerLeftCorner()).scale(0.5),
                (int) map.upperRightCorner().subtract(map.lowerLeftCorner()).scale(1).length(), nutrients);
        map.placeAnyObject(grass);
    }

    public void update(){
        for (IMapElement element : elementsForUpdating){
            element.go();
        }
        double tmp = grassPerTick;
        while(tmp > 1){
            createGrass(7000);
            tmp-=1;
        }
        if (ThreadLocalRandom.current().nextInt(0, 100) < 100*tmp){
            createGrass(7000);
        }
        elementsForUpdating.removeAll(elementsForDeleting);
        elementsForUpdating.addAll(elementsForAdding);
        elementsForDeleting.clear();
        elementsForAdding.clear();
    }

    @Override
    public void died(IMapElement object) {
        elementsForDeleting.add(object);
    }

    @Override
    public void wasBorn(IMapElement object) {
        elementsForAdding.add(object);
        object.registerDeathObserver(this);
    }

}
