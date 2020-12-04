package agh.cs.animalsim;

import java.util.ArrayList;

public class TropicSimulation implements IDeathObserver {

    private ArrayList<IMapElement> elementsForUpdating;
    private ArrayList<IMapElement> elementsForDeleting;

    private IWorldMap map;

    private VectorRandomizer randomizer;

    public TropicSimulation(IWorldMap map){
        this.map = map;
        elementsForDeleting = new ArrayList<>();
        elementsForUpdating = new ArrayList<>();
        randomizer = new VectorRandomizer(map);
    }

    public void createAnimal(boolean carnivore, int size, int speed){
        if (carnivore)
            speed++;
        Animal squirrel = new Animal(map, randomizer.randomVectorOnMapSmart(), carnivore, speed, size);
        elementsForUpdating.add(squirrel);
        map.place(squirrel);
    }

    public void createGrass(){
        TallGrass grass = new TallGrass(map, map.upperRightCorner().subtract(map.lowerLeftCorner()).scale(0.5),
                (int) map.upperRightCorner().subtract(map.lowerLeftCorner()).scale(0.2).length());
        map.placeAnyObject(grass);
    }

    public void update(){
        for (IMapElement element : elementsForUpdating){
            element.go();
        }
        elementsForUpdating.removeAll(elementsForDeleting);
        elementsForDeleting.clear();
    }

    @Override
    public void died(IMapElement object) {
        elementsForDeleting.add(object);
    }

}
