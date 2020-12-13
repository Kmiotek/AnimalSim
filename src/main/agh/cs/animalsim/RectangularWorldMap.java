package agh.cs.animalsim;

import java.util.Set;

public class RectangularWorldMap extends AbstractWorldMap{

    Vector2d size;

    public RectangularWorldMap(int width, int height) {
        super();
        size = new Vector2d(width, height);
    }

    public RectangularWorldMap() {
        this(5,5);
    }

    @Override
    public Vector2d lowerLeftCorner(){
        return new Vector2d(0,0);
    }

    @Override
    public Vector2d upperRightCorner(){
        return size.subtract(v_1_1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.weakPrecedes(upperRightCorner()) && position.weakFollows(lowerLeftCorner()) && !isOccupied(position);
    }

    public boolean canThisMoveTo(Vector2d position, IMapElement object){
        return canMoveTo(position);     //this type of map doesnt need this method
    }


    @Override
    public boolean placeAnyObject(IMapElement object){
        if(object instanceof Animal){
            return super.placeAnyObject(object);
        }
        return false;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        Set<IMapElement> tmp = map.get(position);
        if (tmp == null){
            return null;
        }
        return tmp.iterator().next();
    }

    @Override
    public Set<IMapElement> objectsAt(Vector2d position) {
        return map.get(position);
    }

}
