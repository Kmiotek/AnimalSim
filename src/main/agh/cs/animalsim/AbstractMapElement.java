package agh.cs.animalsim;


import java.util.ArrayList;

public abstract class AbstractMapElement implements IMapElement{

    protected Vector2d position;
    protected IWorldMap mapThatImOn;
    protected ArrayList<IPositionChangeObserver> positionObservers;
    protected ArrayList<ICollisionObserver> collisionObservers;
    protected ArrayList<ILifeObserver> lifeObservers;
    protected int size;
    protected boolean highlighted = false;

    public AbstractMapElement(IWorldMap map, Vector2d initialPosition){
        positionObservers = new ArrayList<>();
        collisionObservers = new ArrayList<>();
        lifeObservers = new ArrayList<>();
        mapThatImOn = map;
        position = new Vector2d(initialPosition);
        size = 1;
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
    public int collisionWithHerbivore() {
        return 0;
    }

    @Override
    public int collisionWithCarnivore() {
        return 0;
    }

    @Override
    public void moved(Vector2d oldPosition) {
        for (IPositionChangeObserver posObs : positionObservers) {
            posObs.positionChanged(oldPosition, this);
        }
    }

    @Override
    public void registerPositionObserver(IPositionChangeObserver observer) {
        this.positionObservers.add(observer);
    }

    @Override
    public void registerCollisionObserver(ICollisionObserver observer) {
        this.collisionObservers.add(observer);
    }

    @Override
    public void registerDeathObserver(ILifeObserver observer){
        this.lifeObservers.add(observer);
    }

    @Override
    public void died(){
        for(ILifeObserver observer : lifeObservers){
            observer.died(this);
        }
    }


    public boolean isDead(){
        return false;
    }

    @Override
    public int getDrawingSize(){
        return 0;
    }

    @Override
    public boolean isCarnivore(){
        return false;
    }

    @Override
    public boolean isGrassy() {
        return false;
    }

    @Override
    public boolean isReadyToMate() {
        return false;
    }

    @Override
    public int getSpeed(){
        return 0;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public void setHighlighted(boolean h){
        highlighted = h;
    }

    @Override
    public void go(){

    }
}
