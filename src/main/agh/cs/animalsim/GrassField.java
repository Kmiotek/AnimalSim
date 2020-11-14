package agh.cs.animalsim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GrassField implements IWorldMap{

    private ArrayList<IMapElement> map;
    private Vector2d lowerLeftCorner;
    private Vector2d upperRightCorner;
    private MapVisualizer myVisualizer;

    public GrassField(int grass){
        map = new ArrayList<>();
        myVisualizer = new MapVisualizer(this);
        for (int i =0;i<grass;i++){
            map.add(new Grass(this, new Vector2d(0,0), (int) Math.sqrt(grass*10)));
        }
        setBounds();
    }

    public void setBounds(){
        if (map.size() < 1){
            lowerLeftCorner = new Vector2d(0,0);
            upperRightCorner = new Vector2d(0,0);
            return;
        }
        Vector2d low = new Vector2d(map.get(0).getPosition());
        Vector2d high = new Vector2d(map.get(0).getPosition());
        for (IMapElement a : map) {
            low = low.lowerLeft(a.getPosition());
            high = high.upperRight(a.getPosition());
        }
        lowerLeftCorner = low;
        upperRightCorner = high;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!isOccupied(position)){
            return false;
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
    public boolean place(Animal animal) {
        return placeAnyObject(animal);
    }

    @Override
    public boolean placeAnyObject(IMapElement object){
        if (canThisMoveTo(object.getPosition(), object)){
            map.add(object);
            return true;
        }
        return false;
    }

    @Override
    public void callOnCollision(Vector2d position) {
        if(isOccupied(position)){
            objectAt(position).onCollision();
        }
    }

    public int numberOfPositionsOccupiedInSquare(Vector2d lowerLeft, Vector2d upperRight) {
        Set<Vector2d> names = new HashSet<>();
        for (IMapElement el : map) {
            if (el.getPosition().follows(lowerLeft) && el.getPosition().precedes(upperRight)) {
                names.add(el.getPosition());
            }
        }
        return names.size();
    }

    public int numberOfPositionsOccupied(){
        return numberOfPositionsOccupiedInSquare(lowerLeftCorner, upperRightCorner);
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        IMapElement current = null;
        for (IMapElement a : map) {
            if(a.getPosition().equals(position) && (current == null || current.getCollisionPriority() < a.getCollisionPriority())){
                current = a;
            }
        }
        return current;
    }

    public String toString(){
        setBounds();
        return myVisualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
