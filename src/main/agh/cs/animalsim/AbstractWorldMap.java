package agh.cs.animalsim;

import java.util.*;

public class AbstractWorldMap implements IWorldMap {

    protected Map<Vector2d, Set<IMapElement>> map;
    protected MapVisualizer myVisualizer;

    protected Vector2d v_1_1;

    public AbstractWorldMap(){
        map = new LinkedHashMap<>();
        myVisualizer = new MapVisualizer(this);
        v_1_1 = new Vector2d(1,1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean canThisMoveTo(Vector2d position, IMapElement object) {
        return false;
    }


    @Override
    public void callCollisionWithHerbivore(Vector2d position) {
        if(isOccupied(position)){
            objectAt(position).collisionWithHerbivore();
        }
    }

    @Override
    public void callCollisionWithCarnivore(Vector2d position) {
        if(isOccupied(position)){
            objectAt(position).collisionWithCarnivore();
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public boolean place(Animal animal) {
        return placeAnyObject(animal);
    }

    @Override
    public boolean placeAnyObject(IMapElement object){
        if (canThisMoveTo(object.getPosition(), object)){
            object.setMap(this);
            object.addObserver(this);
            if (map.containsKey(object.getPosition())){
                map.get(object.getPosition()).add(object);
            } else {
                Set<IMapElement> n = new HashSet<>();
                n.add(object);
                map.put(object.getPosition(), n);
            }
            return true;
        }
        return false;
    }

    public String toString(){
        setBounds();
        return myVisualizer.draw(lowerLeftCorner(), upperRightCorner());
    }

    protected Vector2d lowerLeftCorner(){
        return null;
    }

    protected Vector2d upperRightCorner(){
        return null;
    }

    public void setBounds(){

    }

    @Override
    public void positionChanged(Vector2d oldPosition, IMapElement what) {
        Set<IMapElement> square = map.get(oldPosition);
        if (square.size() < 2){
            map.remove(oldPosition);
            if (isOccupied(what.getPosition())){
                map.get(what.getPosition()).add(what);
            } else {
                map.put(what.getPosition(), square);
            }
        } else {
            map.get(oldPosition).remove(what);
            if (isOccupied(what.getPosition())){
                map.get(what.getPosition()).add(what);
            } else {
                Set<IMapElement> newSquare = new HashSet<>();
                newSquare.add(what);
                map.put(what.getPosition(), newSquare);
            }
        }
    }
}
