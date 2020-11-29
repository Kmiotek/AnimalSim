package agh.cs.animalsim;

import agh.cs.animalsim.swing.MapDisplayer;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver, ICollisionObserver {

    protected Map<Vector2d, Set<IMapElement>> map;
    protected MapVisualizer myVisualizer;
    protected MapDisplayer displayer;

    protected Vector2d v_1_1;

    public AbstractWorldMap(){
        map = new LinkedHashMap<>();
        myVisualizer = new MapVisualizer(this);
        displayer = new MapDisplayer(this);
        v_1_1 = new Vector2d(1,1);
    }


    @Override
    public int callCollisionWithHerbivore(Vector2d position) {
        if(isOccupied(position)){
            return objectAt(position).collisionWithHerbivore();
        }
        return 0;
    }

    @Override
    public int callCollisionWithCarnivore(Vector2d position) {
        if(isOccupied(position)){
            return objectAt(position).collisionWithCarnivore();
        }
        return 0;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!isOccupied(position)){
            return true;
        }
        return objectAt(position).getCollisionPriority() < 1;
    }

    @Override
    public boolean canThisMoveTo(Vector2d position, IMapElement object) {
        if (!isOccupied(position)){
            return true;
        }
        return objectAt(position).getCollisionPriority() < object.getCollisionPriority();
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


    @Override
    public boolean place(Animal animal) {
        return placeAnyObject(animal);
    }

    @Override
    public Set<Vector2d> getObjectsPositions() {
        return map.keySet();
    }

    protected boolean placeOnPosition(IMapElement object, Vector2d position){
        if (canThisMoveTo(position, object)) {
            object.registerPositionObserver(this);
            object.registerCollisionObserver(this);
            if (map.containsKey(position)) {
                map.get(position).add(object);
            } else {
                Set<IMapElement> n = new HashSet<>();
                n.add(object);
                map.put(position, n);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean placeAnyObject(IMapElement object){
        if (placeOnPosition(object, object.getPosition())){
            return true;
        }
        throw new IllegalArgumentException("Position " + object.getPosition() + " is not available");
    }

    @Override
    public void display() {
        displayer.display(lowerLeftCorner(),upperRightCorner());
    }

    public String toString(){
        return myVisualizer.draw(lowerLeftCorner(), upperRightCorner());
    }

    public Vector2d lowerLeftCorner(){
        return null;
    }

    public Vector2d upperRightCorner(){
        return null;
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
