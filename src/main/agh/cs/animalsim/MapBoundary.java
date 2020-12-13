package agh.cs.animalsim;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{

    private SortedSet<IMapElement> orderedByX;
    private SortedSet<IMapElement> orderedByY;

    public MapBoundary(){
        orderedByX = new TreeSet<>((el1, el2) -> {
            if (el1.getPosition().x < el2.getPosition().x) {
                return -1;
            }
            if (el1.getPosition().x > el2.getPosition().x) {
                return 1;
            }
            if (el1.getPosition().y < el2.getPosition().y) {
                return -1;
            }
            if (el1.getPosition().y > el2.getPosition().y) {
                return 1;
            }
            return Integer.compare(el1.getCollisionPriority(), el2.getCollisionPriority());
        }
        );
        orderedByY = new TreeSet<IMapElement>((el1, el2) -> {
            if (el1.getPosition().y < el2.getPosition().y){
                return -1;
            }
            if (el1.getPosition().y > el2.getPosition().y){
                return 1;
            }
            if (el1.getPosition().x < el2.getPosition().x){
                return -1;
            }
            if (el1.getPosition().x > el2.getPosition().x){
                return 1;
            }
            return Integer.compare(el1.getCollisionPriority(), el2.getCollisionPriority());
        }
        );
    }


    public void addElement(IMapElement object){
        orderedByX.add(object);
        orderedByY.add(object);
    }

    public Vector2d getLowerLeft(){
        if (orderedByX.size() < 1){
            return new Vector2d(0,0);
        }
        return orderedByX.first().getPosition().lowerLeft(orderedByY.first().getPosition());
    }

    public Vector2d getUpperRight(){
        if (orderedByX.size() < 1){
            return new Vector2d(0,0);
        }
        return orderedByX.last().getPosition().upperRight(orderedByY.last().getPosition());
    }

    @Override
    public void positionChanged(Vector2d oldPosition, IMapElement what) {
        orderedByY.add(what);
        orderedByX.add(what);
    }
}
