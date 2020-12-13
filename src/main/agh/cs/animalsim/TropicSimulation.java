package agh.cs.animalsim;

import java.util.ArrayList;

public class TropicSimulation implements ILifeObserver {

    private ArrayList<IMapElement> elementsForUpdating;
    private ArrayList<IMapElement> elementsForDeleting;
    private ArrayList<IMapElement> elementsForAdding;

    private IWorldMap map;

    private VectorRandomizer randomizer;

    public TropicSimulation(IWorldMap map){
        this.map = map;
        elementsForDeleting = new ArrayList<>();
        elementsForUpdating = new ArrayList<>();
        elementsForAdding = new ArrayList<>();
        randomizer = new VectorRandomizer(map);
    }

    public void createAnimal(boolean carnivore, int size, int speed){
        TropicAnimal squirrel = new TropicAnimal(map, randomizer.randomVectorOnMapSmart(), carnivore, speed, size);
        elementsForUpdating.add(squirrel);
        squirrel.registerDeathObserver(this);
        map.place(squirrel);
    }

    public void createGrass(){
        TallGrass grass = new TallGrass(map, map.upperRightCorner().subtract(map.lowerLeftCorner()).scale(0.5),
                (int) map.upperRightCorner().subtract(map.lowerLeftCorner()).scale(1).length());
        map.placeAnyObject(grass);
    }

    public void update(){
        for (IMapElement element : elementsForUpdating){
            element.go();
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
