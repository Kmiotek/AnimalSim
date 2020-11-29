package agh.cs.animalsim;

import java.util.HashSet;
import java.util.Set;

public class TropicMap extends AbstractWorldMap{

    final Vector2d size;
    int jungleSize;

    public TropicMap(int sizex, int sizey, int jungleSize){
        super();
        this.size = new Vector2d(sizex, sizey);
        this.jungleSize = jungleSize;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        Set<IMapElement> set = map.get(position.dualMod(size));
        if (set == null){
            return null;
        }
        IMapElement current = null;
        int currentPriority = -1;
        for (IMapElement el : set) {
            if (el.getCollisionPriority() > currentPriority){
                current = el;
                currentPriority = el.getCollisionPriority();
            }
        }
        return current;
    }

    @Override
    public Set<IMapElement> objectsAt(Vector2d position) {
        return map.get(position.dualMod(size));
    }

    @Override
    public boolean placeAnyObject(IMapElement object){
        Vector2d newPosition = object.getPosition().dualMod(size);
        if (placeOnPosition(object, newPosition)){
            return true;
        }
        throw new IllegalArgumentException("Position " + object.getPosition() + " understood as " + newPosition + " is not available");
    }

    @Override
    public Set<Vector2d> getObjectsPositions() {
        Set<Vector2d> ret = new HashSet<>();
        for(Vector2d el : map.keySet()){
            ret.add(el.dualMod(size));
        }
        return ret;
    }

    @Override
    public void positionChanged(Vector2d oldPosition1, IMapElement what) {
        Vector2d oldPosition = oldPosition1.dualMod(size);
        Vector2d newPosition = what.getPosition().dualMod(size);
        Set<IMapElement> square = map.get(oldPosition);
        if (square.size() < 2){
            map.remove(oldPosition);
            if (isOccupied(newPosition)){
                map.get(newPosition).add(what);
            } else {
                map.put(newPosition, square);
            }
        } else {
            map.get(oldPosition).remove(what);
            if (isOccupied(newPosition)){
                map.get(newPosition).add(what);
            } else {
                Set<IMapElement> newSquare = new HashSet<>();
                newSquare.add(what);
                map.put(newPosition, newSquare);
            }
        }
    }

    public Vector2d lowerLeftCorner(){
        return new Vector2d(0,0);
    }

    public Vector2d upperRightCorner(){
        return size;
    }
}
