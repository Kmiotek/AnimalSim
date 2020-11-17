package agh.cs.animalsim;

import java.util.ArrayList;

public abstract class AbstractMapElement implements IMapElement{

    protected Vector2d position;
    protected IWorldMap mapThatImOn;
    protected int size;
    protected IPositionChangeObserver observer;

    public AbstractMapElement(IWorldMap map, Vector2d initialPosition){
        mapThatImOn = map;
        position = new Vector2d(initialPosition);
        size = 1;
        observer = map;
    }

    public AbstractMapElement(IWorldMap map){
        this(map, new Vector2d(0,0));
    }

    @Override
    public int getCollisionPriority() {
        return size;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public void collisionWithHerbivore() {

    }

    @Override
    public void collisionWithCarnivore() {

    }

    @Override
    public void updateObservers(Vector2d oldPosition) {
        observer.positionChanged(oldPosition, this);
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        this.observer =  observer;
    }

    @Override
    public void setMap(IWorldMap map) {
        mapThatImOn = map;
    }
}
