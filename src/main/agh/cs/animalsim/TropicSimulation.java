package agh.cs.animalsim;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TropicSimulation implements ILifeObserver {

    private ArrayList<IMapElement> elementsForUpdating;
    private ArrayList<IMapElement> elementsForDeleting;
    private ArrayList<IMapElement> elementsForAdding;

    private TropicMap map;

    private VectorRandomizer randomizer;

    private double grassPerTick;

    ArrayList<ILifeObserver> observers;

    public TropicSimulation(TropicMap map, ArrayList<ILifeObserver> observers, double grassPerTick){
        this.map = map;
        elementsForDeleting = new ArrayList<>();
        elementsForUpdating = new ArrayList<>();
        elementsForAdding = new ArrayList<>();
        randomizer = new VectorRandomizer(map);
        this.grassPerTick = grassPerTick;
        this.observers = observers;
    }

    public void createAnimal(boolean carnivore, int size, int speed, int initialEnergy, int meatQuality,
                             float moveEfficiency, int chanceOfLooking, int vision){
        TropicAnimal squirrel = new TropicAnimal(map, randomizer.randomVectorOnMapSmart(), carnivore, speed, size,
                initialEnergy, meatQuality, moveEfficiency, chanceOfLooking, vision);
        elementsForUpdating.add(squirrel);
        squirrel.registerDeathObserver(this);
        for (ILifeObserver observer : observers){
            squirrel.registerDeathObserver(observer);
        }
        map.place(squirrel);
    }

    public void createGrass(int nutrients){
        Grass grass = new Grass(map,
                randomizer.randomVectorInRangeStupid(map.junglePos(), map.junglePos().add(map.jungleSize)),
                nutrients);
        Grass grass2 = new Grass(map,
                randomizer.randomVectorInRingStupid(map.lowerLeftCorner(), map.upperRightCorner(),
                        map.junglePos(), map.junglePos().add(map.jungleSize)),
                nutrients);
        for (ILifeObserver observer : observers){
            observer.wasBorn(grass);
            observer.wasBorn(grass2);
        }
        map.placeAnyObject(grass);
        map.placeAnyObject(grass2);
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
