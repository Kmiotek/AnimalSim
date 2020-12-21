package agh.cs.animalsim;

import java.util.HashSet;
import java.util.Set;

public class TropicMap extends AbstractWorldMap{

    public final Vector2d size;
    public Vector2d jungleSize;

    public TropicMap(int sizeX, int sizeY, int jungleSizeX, int jungleSizeY){
        super();
        this.size = new Vector2d(sizeX, sizeY);
        this.jungleSize = new Vector2d(jungleSizeX, jungleSizeY);

    }

    @Override
    protected Vector2d modifyPosition(Vector2d position){
        return position.modulo(size);
    }

    public Vector2d junglePos(){
        return new Vector2d((size.x - jungleSize.x) / 2, (size.y - jungleSize.y) / 2);
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        Set<IMapElement> set = map.get(position.modulo(size));
        return largestObjectIn(set);
    }

    private IMapElement largestObjectIn(Set<IMapElement> set){
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
        return map.get(position.modulo(size));
    }

    @Override
    public boolean placeAnyObject(IMapElement object){
        Vector2d newPosition = object.getPosition().modulo(size);
        if (placeOnPosition(object, newPosition)){
            return true;
        }
        throw new IllegalArgumentException("Position " + object.getPosition() + " understood as " + newPosition + " is not available");
    }

    @Override
    public Set<Vector2d> getObjectsPositions() {
        Set<Vector2d> ret = new HashSet<>();
        for(Vector2d el : map.keySet()){
            ret.add(el.modulo(size));
        }
        return ret;
    }


    @Override
    public Set<Drawable> getDrawableObjects(){
        Set<Drawable> ret = new HashSet<>();
        for(Set<IMapElement> el : map.values()){
            IMapElement object = largestObjectIn(el);
            ret.add(new Drawable(object.getPosition(), object.getColor(), object.getDrawingSize()));
        }
        return ret;
    }


    @Override
    public void died(IMapElement object) {
        Set<IMapElement> square = objectsAt(object.getPosition().modulo(size));
        if (square.size() < 2){
            map.remove(object.getPosition().modulo(size));
        } else {
            square.remove(object);
        }
    }

    public Vector2d lowerLeftCorner(){
        return new Vector2d(0,0);
    }

    public Vector2d upperRightCorner(){
        return size;
    }


}
