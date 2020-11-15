package agh.cs.animalsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RectangularWorldMap extends AbstractWorldMap{

    //private final Map<Vector2d, Animal> map;

    public RectangularWorldMap(int width, int height) {
        lowerLeftCorner = new Vector2d(0,0);
        upperRightCorner = new Vector2d(width-1, height-1);
        //map = new HashMap<>();
        map = new ArrayList<>();
        myVisualizer = new MapVisualizer(this);
    }

    public RectangularWorldMap() {
        this(5,5);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.weakPrecedes(upperRightCorner) && position.weakFollows(lowerLeftCorner) && !isOccupied(position);
    }

    public boolean canThisMoveTo(Vector2d position, IMapElement object){
        return canMoveTo(position);     //this type of map doesnt need this method
    }

    @Override
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPosition())){
            return false;
        }
        //map.put(animal.getPosition(), animal);
        map.add(animal);
        return true;
    }

    @Override
    public boolean placeAnyObject(IMapElement object){
        if(object instanceof Animal){
            return place((Animal) object);
        }
        return false;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        //return map.get(position);
        for (IMapElement a : map) {
            if(a.getPosition().equals(position)){
                return a;
            }
        }
        return null;
    }

}
