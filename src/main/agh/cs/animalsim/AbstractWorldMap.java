package agh.cs.animalsim;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver, ICollisionObserver, ILifeObserver {

    protected ConcurrentMap<Vector2d, Set<IMapElement>> map;
    protected MapVisualizer myVisualizer;

    protected Vector2d v_1_1;

    public AbstractWorldMap(){
        map = new ConcurrentHashMap<>();
        myVisualizer = new MapVisualizer(this);
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

    public Set<Drawable> getDrawableObjects(){
        return null;
    }

    protected boolean placeOnPosition(IMapElement object, Vector2d position){
        if (canThisMoveTo(position, object)) {
            object.registerPositionObserver(this);
            object.registerCollisionObserver(this);
            object.registerLifeObserver(this);
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

    public String toString(){
        return myVisualizer.draw(lowerLeftCorner(), upperRightCorner());
    }

    public Vector2d lowerLeftCorner(){
        return null;
    }

    public Vector2d upperRightCorner(){
        return null;
    }

    public Vector2d getDimensions(){
        return upperRightCorner().subtract(lowerLeftCorner());
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

    @Override
    public void died(IMapElement object) {
        Set<IMapElement> square = objectsAt(object.getPosition());
        if (square.size() < 2){
            map.remove(object.getPosition());
        } else {
            square.remove(object);
        }
    }

    @Override
    public void wasBorn(IMapElement object) {
        placeAnyObject(object);
    }

    public int numberOfPositionsOccupiedInSquare(Vector2d lowerLeft, Vector2d upperRight) {
        Set<Vector2d> names = new HashSet<>();
        for (Vector2d el : map.keySet()) {
            if (el.weakFollows(lowerLeft) && el.weakPrecedes(upperRight)) {
                names.add(el);
            }
        }
        return names.size();
    }

    @Override
    public Animal animalClosestTo(Vector2d position){
        double minDist = upperRightCorner().dist(lowerLeftCorner());
        Animal current = null;
        for (Vector2d v : map.keySet()){
            double dist = v.dist(position);
            IMapElement obj = objectAt(v);
            if (dist < minDist && obj instanceof Animal && dist < obj.getSize()*8){
                minDist = dist;
                current = (Animal) obj;
            }
        }
        return current;
    }
}
