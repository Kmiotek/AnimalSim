package agh.cs.animalsim;

import java.util.ArrayList;

public class AbstractWorldMap implements IWorldMap{

    protected ArrayList<IMapElement> map;
    protected Vector2d lowerLeftCorner;
    protected Vector2d upperRightCorner;
    protected MapVisualizer myVisualizer;

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean canThisMoveTo(Vector2d position, IMapElement object) {
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean placeAnyObject(IMapElement object) {
        return false;
    }

    @Override
    public void callOnCollision(Vector2d position) {
        if(isOccupied(position)){
            objectAt(position).onCollision();
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

    public String toString(){
        setBounds();
        return myVisualizer.draw(lowerLeftCorner, upperRightCorner);
    }

    public void setBounds(){

    }
}
